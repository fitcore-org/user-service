package com.fitcore.users.application.service

import com.fitcore.users.domain.model.common.UserId
import com.fitcore.users.domain.model.employee.Employee
import com.fitcore.users.domain.port.`in`.employee.FindEmployeeUseCase
import com.fitcore.users.domain.port.`in`.employee.ManageEmployeeUseCase
import com.fitcore.users.domain.port.out.employee.EmployeeRepository
import com.fitcore.users.domain.port.out.employee.event.EmployeeEventPublisher
import com.fitcore.users.infrastructure.util.EnumMappers
import com.fitcore.users.application.exception.CpfAlreadyRegisteredException
import com.fitcore.users.application.exception.EmailAlreadyRegisteredException
import com.fitcore.users.application.exception.EmployeeNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class EmployeeService(
    private val employeeRepository: EmployeeRepository,
    private val employeeEventPublisher: EmployeeEventPublisher
) : ManageEmployeeUseCase, FindEmployeeUseCase {
    
    override fun registerEmployee(
        name: String,
        email: String,
        cpf: String,
        birthDate: LocalDate,
        phone: String,
        roleType: String,
        hireDate: LocalDate
    ): Employee {
        // Verificar se email já existe
        if (employeeRepository.findByEmail(email) != null) {
            throw EmailAlreadyRegisteredException(email)
        }
        
        // Verificar se CPF já existe
        if (employeeRepository.findByCpf(cpf) != null) {
            throw CpfAlreadyRegisteredException(cpf)
        }
        
        // Converter string do role para enum
        val role = EnumMappers.toRoleDomain(roleType)
        
        // Criar entidade de domínio
        val employee = Employee.create(
            name = name,
            email = email,
            cpf = cpf,
            birthDate = birthDate,
            phone = phone,
            role = role,
            hireDate = hireDate
        )
        
        // Persistir via repositório
        val savedEmployee = employeeRepository.save(employee)
        
        // Publicar evento
        employeeEventPublisher.publishEmployeeCreated(savedEmployee)

        return savedEmployee
    }

    override fun findAll(): List<Employee> {
        return employeeRepository.findAll()
    }
    
    override fun findById(id: UserId): Employee? {
        return employeeRepository.findById(id)
    }
    
    override fun findByEmail(email: String): Employee? {
        return employeeRepository.findByEmail(email)
    }
    
    override fun findByCpf(cpf: String): Employee? {
        return employeeRepository.findByCpf(cpf)
    }

    override fun findByRole(roleType: String): List<Employee> {
        val role = EnumMappers.toRoleDomain(roleType)
        return employeeRepository.findByRole(role)
    }

    override fun findAllActive(): List<Employee> {
        return employeeRepository.findAllActive()
    }
    
    override fun updateEmployee(
        id: UserId,
        name: String,
        email: String,
        phone: String,
        roleType: String
    ): Employee {
        val employee = findById(id) ?: throw EmployeeNotFoundException(id.toString())
        
        // Verificar se o novo email já existe (para outro funcionário)
        if (email != employee.email && employeeRepository.findByEmail(email) != null) {
            throw EmailAlreadyRegisteredException(email)
        }
        
        val role = EnumMappers.toRoleDomain(roleType)
        val updatedEmployee = employee.update(name, email, phone, role)
        val savedEmployee = employeeRepository.save(updatedEmployee)
        
        // Publicar evento
        employeeEventPublisher.publishEmployeeUpdated(savedEmployee)
        
        return savedEmployee
    }
    
    override fun terminateEmployee(id: UserId, terminationDate: LocalDate): Employee {
        val employee = findById(id) ?: throw EmployeeNotFoundException(id.toString())
        val terminatedEmployee = employee.terminate(terminationDate)
        val savedEmployee = employeeRepository.save(terminatedEmployee)
        
        // Publicar evento
        employeeEventPublisher.publishEmployeeTerminated(savedEmployee)
        
        return savedEmployee
    }
    
    override fun reactivateEmployee(id: UserId): Employee {
        val employee = findById(id) ?: throw EmployeeNotFoundException(id.toString())
        val reactivatedEmployee = employee.reactivate()
        val savedEmployee = employeeRepository.save(reactivatedEmployee)
        
        // Publicar evento
        employeeEventPublisher.publishEmployeeUpdated(savedEmployee)
        
        return savedEmployee
    }
    
    override fun deleteEmployee(id: UserId): Boolean {
        // Verificar se funcionário existe
        findById(id) ?: throw EmployeeNotFoundException(id.toString())
        
        return employeeRepository.deleteById(id)
    }
}