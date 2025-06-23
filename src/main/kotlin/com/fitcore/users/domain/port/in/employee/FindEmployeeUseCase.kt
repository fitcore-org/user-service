package com.fitcore.users.domain.port.`in`.employee

import com.fitcore.users.domain.model.common.UserId
import com.fitcore.users.domain.model.employee.Employee

interface FindEmployeeUseCase {
    fun findById(id: UserId): Employee?
    fun findByEmail(email: String): Employee?
    fun findByCpf(cpf: String): Employee?
    fun findByRole(roleType: String): List<Employee>
    fun findAllActive(): List<Employee>
    fun findAll(): List<Employee>
}