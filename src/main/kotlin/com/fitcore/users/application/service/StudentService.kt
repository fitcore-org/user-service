package com.fitcore.users.application.service

import com.fitcore.users.domain.model.common.UserId
import com.fitcore.users.domain.model.student.Student
import com.fitcore.users.domain.model.student.StudentPlan
import com.fitcore.users.domain.port.`in`.student.FindStudentUseCase
import com.fitcore.users.domain.port.`in`.student.ManageStudentUseCase
import com.fitcore.users.domain.port.out.student.StudentRepository
import com.fitcore.users.domain.port.out.student.event.StudentEventPublisher
import com.fitcore.users.infrastructure.util.EnumMappers
import com.fitcore.users.application.exception.CpfAlreadyRegisteredException
import com.fitcore.users.application.exception.EmailAlreadyRegisteredException
import com.fitcore.users.application.exception.StudentNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class StudentService(
    private val studentRepository: StudentRepository,
    private val studentEventPublisher: StudentEventPublisher
) : ManageStudentUseCase, FindStudentUseCase {
    
    override fun registerStudent(
        name: String,
        email: String,
        cpf: String,
        birthDate: LocalDate,
        phone: String,
        planType: String,
        weight: Double?,
        height: Int?,
        registrationDate: LocalDateTime? 
    ): Student {
        // Verificar se email já existe
        if (studentRepository.findByEmail(email) != null) {
            throw EmailAlreadyRegisteredException(email)
        }
        
        // Verificar se CPF já existe
        if (studentRepository.findByCpf(cpf) != null) {
            throw CpfAlreadyRegisteredException(cpf)
        }
        
        // Converter string do plano para enum usando a classe centralizada
        val plan = EnumMappers.toPlanDomain(planType)
        
        // Criar entidade de domínio
        val student = if (registrationDate != null) {
            // Se uma data foi fornecida (pelo seeder), use-a.
            Student.createWithRegistrationDate(
                name = name,
                email = email,
                cpf = cpf,
                birthDate = birthDate,
                phone = phone,
                plan = plan,
                weight = weight,
                height = height,
                registrationDate = registrationDate // <-- Usa a data 
            )
        } else {
            // Se nenhuma data foi fornecida (uso normal da API), cria com a data atual.
            Student.create(
                name = name,
                email = email,
                cpf = cpf,
                birthDate = birthDate,
                phone = phone,
                plan = plan,
                weight = weight,
                height = height
            )
        }
        
        // Persistir via repositório
        val savedStudent = studentRepository.save(student)

        // Publicar evento usando a abstração
        studentEventPublisher.publishStudentCreated(savedStudent)

        return savedStudent
    }

    override fun findAll(): List<Student> {
        return studentRepository.findAll()
    }
    
    override fun findById(id: UserId): Student {
        return studentRepository.findById(id) 
            ?: throw StudentNotFoundException(id.toString())
    }
    
    override fun findByEmail(email: String): Student? {
        return studentRepository.findByEmail(email)
    }
    
    override fun findByCpf(cpf: String): Student? {
        return studentRepository.findByCpf(cpf)
    }

    override fun findByPlan(planType: String): List<Student> {
        val plan = EnumMappers.toPlanDomain(planType)
        return studentRepository.findByPlan(plan)
    }

    override fun findAllActive(): List<Student> {
        return studentRepository.findAllActive()
    }
    
    override fun updateStudent(
        id: UserId, 
        name: String, 
        email: String, 
        phone: String, 
        planType: String, 
        weight: Double?, 
        height: Int?,
        profileUrl: String?
    ): Student {
        val student = findById(id)
        
        // Verificar se o novo email já existe (para outro estudante)
        if (email != student.email && studentRepository.findByEmail(email) != null) {
            throw EmailAlreadyRegisteredException(email)
        }
        
        val plan = EnumMappers.toPlanDomain(planType)
        val updatedStudent = student.update(name, email, phone, plan, weight, height)
        .withProfileUrl(profileUrl)
        return studentRepository.save(updatedStudent)
    }

    override fun changePlan(id: UserId, planType: String): Student {
        val student = findById(id)
        val plan = EnumMappers.toPlanDomain(planType)
        val updatedStudent = student.update(
            name = student.name,
            email = student.email,
            phone = student.phone,
            plan = plan,
            weight = student.weight,
            height = student.height
        )

        // Publicando o evento
        val savedStudent = studentRepository.save(updatedStudent)
        studentEventPublisher.publishStudentPlanChanged(savedStudent)

        return savedStudent
    }
    
    override fun updatePhysicalData(id: UserId, weight: Double?, height: Int?): Student {
        val student = findById(id)
        val updatedStudent = student.updatePhysicalData(weight, height)
        return studentRepository.save(updatedStudent)
    }
    
    override fun activateStudent(id: UserId): Student {
        val student = findById(id)
        val activatedStudent = student.activate()

        // Publicando evento
        val savedStudent = studentRepository.save(activatedStudent)
        studentEventPublisher.publishStudentStatusChanged(savedStudent)
        return savedStudent
    }
    
    override fun deactivateStudent(id: UserId): Student {
        val student = findById(id)
        val deactivatedStudent = student.deactivate()

        // Publicando evento
        val savedStudent = studentRepository.save(deactivatedStudent)
        studentEventPublisher.publishStudentStatusChanged(savedStudent)
        return savedStudent
    }
    
    override fun deleteStudent(id: UserId): Boolean {
        // Verificar se estudante existe
        findById(id)

        // Publicando evento
        val deleted = studentRepository.deleteById(id)
        if (deleted) {
            studentEventPublisher.publishStudentDeleted(id)
        }
        return deleted
    }
}