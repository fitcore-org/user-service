package com.fitcore.users.domain.port.`in`.employee

import com.fitcore.users.domain.model.common.UserId
import com.fitcore.users.domain.model.employee.Employee
import java.time.LocalDate

interface ManageEmployeeUseCase {
    fun registerEmployee(
        name: String,
        email: String,
        cpf: String,
        birthDate: LocalDate,
        phone: String,
        roleType: String,
        hireDate: LocalDate = LocalDate.now()
    ): Employee
    
    fun updateEmployee(
        id: UserId,
        name: String,
        email: String,
        phone: String,
        roleType: String,
        profileUrl: String? = null
    ): Employee

    fun changeRole(id: UserId, roleType: String): Employee
    
    fun terminateEmployee(
        id: UserId,
        terminationDate: LocalDate = LocalDate.now()
    ): Employee
    
    fun reactivateEmployee(id: UserId): Employee
    
    fun deleteEmployee(id: UserId): Boolean
}