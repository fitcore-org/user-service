package com.fitcore.users.domain.port.out.employee.event

import com.fitcore.users.domain.model.employee.Employee
import com.fitcore.users.domain.model.common.UserId

interface EmployeeEventPublisher {
    fun publishEmployeeCreated(employee: Employee)
    fun publishEmployeeUpdated(employee: Employee)
    fun publishEmployeeTerminated(employee: Employee)

    fun publishEmployeeRoleChanged(employee: Employee)
    fun publishEmployeeStatusChanged(employee: Employee)
    fun publishEmployeeDeleted(employeeId: UserId)
}