package com.fitcore.users.domain.port.out.employee.event

import com.fitcore.users.domain.model.employee.Employee

interface EmployeeEventPublisher {
    fun publishEmployeeCreated(employee: Employee)
    fun publishEmployeeUpdated(employee: Employee)
    fun publishEmployeeTerminated(employee: Employee)
}