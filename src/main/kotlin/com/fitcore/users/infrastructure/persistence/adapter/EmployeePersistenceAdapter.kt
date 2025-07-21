package com.fitcore.users.infrastructure.persistence.adapter

import com.fitcore.users.domain.model.common.UserId
import com.fitcore.users.domain.model.employee.Employee
import com.fitcore.users.domain.model.employee.Role
import com.fitcore.users.domain.port.out.employee.EmployeeRepository
import com.fitcore.users.infrastructure.persistence.mapper.EmployeePersistenceMapper
import com.fitcore.users.infrastructure.persistence.repository.EmployeeJpaRepository
import com.fitcore.users.infrastructure.util.EnumMappers
import org.springframework.stereotype.Component

@Component
class EmployeePersistenceAdapter(
    private val employeeJpaRepository: EmployeeJpaRepository,
    private val mapper: EmployeePersistenceMapper
) : EmployeeRepository {
    
    override fun save(employee: Employee): Employee {
        val entity = mapper.toEntity(employee)
        val savedEntity = employeeJpaRepository.save(entity)
        return mapper.toDomain(savedEntity)
    }
    
    override fun findById(id: UserId): Employee? {
        return employeeJpaRepository.findById(id.value)
            .map { mapper.toDomain(it) }
            .orElse(null)
    }
    
    override fun findByEmail(email: String): Employee? {
        return employeeJpaRepository.findByEmail(email)?.let { mapper.toDomain(it) }
    }
    
    override fun findByCpf(cpf: String): Employee? {
        return employeeJpaRepository.findByCpf(cpf)?.let { mapper.toDomain(it) }
    }
    
    override fun findByRole(role: Role): List<Employee> {
        return employeeJpaRepository.findByRole(role).map { mapper.toDomain(it) }
    }
    
    override fun findAllActive(): List<Employee> {
        return employeeJpaRepository.findByActive(true).map { mapper.toDomain(it) }
    }
    
    override fun findAll(): List<Employee> {
        return employeeJpaRepository.findAll().map { mapper.toDomain(it) }
    }
    
    override fun deleteById(id: UserId): Boolean {
        return try {
            employeeJpaRepository.deleteById(id.value)
            true
        } catch (e: Exception) {
            false
        }
    }
}