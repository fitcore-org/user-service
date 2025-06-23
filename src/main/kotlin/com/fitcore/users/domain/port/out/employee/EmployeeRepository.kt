package com.fitcore.users.domain.port.out.employee

import com.fitcore.users.domain.model.common.UserId
import com.fitcore.users.domain.model.employee.Employee
import com.fitcore.users.domain.model.employee.Role

interface EmployeeRepository {
    fun save(employee: Employee): Employee
    fun findById(id: UserId): Employee?
    fun findByEmail(email: String): Employee?
    fun findByCpf(cpf: String): Employee?
    fun findByRole(role: Role): List<Employee>
    fun findAllActive(): List<Employee>
    fun findAll(): List<Employee>
    fun deleteById(id: UserId): Boolean
}