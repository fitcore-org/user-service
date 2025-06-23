package com.fitcore.users.application.service

import com.fitcore.users.domain.model.common.UserId
import com.fitcore.users.domain.model.student.Student
import com.fitcore.users.domain.model.student.StudentPlan
import com.fitcore.users.domain.port.`in`.student.FindStudentUseCase
import com.fitcore.users.domain.port.`in`.student.ManageStudentUseCase
import com.fitcore.users.domain.port.out.student.StudentRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class StudentService(
    private val studentRepository: StudentRepository
) : ManageStudentUseCase, FindStudentUseCase {
    
    override fun registerStudent(
        name: String,
        email: String,
        cpf: String,
        birthDate: LocalDate,
        phone: String,
        planType: String,
        weight: Double?,
        height: Int?
    ): Student {
        // Converter string do plano para enum
        val plan = StudentPlan.fromString(planType)
        
        // Criar entidade de domínio
        val student = Student.create(
            name = name,
            email = email,
            cpf = cpf,
            birthDate = birthDate,
            phone = phone,
            plan = plan,
            weight = weight,
            height = height
        )
        
        // Persistir via repositório
        return studentRepository.save(student)
    }

    override fun findAll(): List<Student> {
        return studentRepository.findAll()
    }
    
    override fun findById(id: UserId): Student? = null
    override fun findByEmail(email: String): Student? = null
    override fun findByCpf(cpf: String): Student? = null
    override fun findByPlan(planType: String): List<Student> = emptyList()
    override fun findAllActive(): List<Student> = emptyList()
    override fun updateStudent(id: UserId, name: String, email: String, phone: String, planType: String, weight: Double?, height: Int?): Student {
        throw NotImplementedError("Not implemented yet")
    }
    override fun updatePhysicalData(id: UserId, weight: Double?, height: Int?): Student {
        throw NotImplementedError("Not implemented yet")
    }
    override fun activateStudent(id: UserId): Student {
        throw NotImplementedError("Not implemented yet")
    }
    override fun deactivateStudent(id: UserId): Student {
        throw NotImplementedError("Not implemented yet")
    }
    override fun deleteStudent(id: UserId): Boolean {
        throw NotImplementedError("Not implemented yet")
    }
}