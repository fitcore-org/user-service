package com.fitcore.users.infrastructure.web.dto.employee

import java.time.LocalDate

data class EmployeeRequestDto(
    val name: String,
    val email: String,
    val cpf: String,
    val birthDate: LocalDate,
    val phone: String,
    val roleType: String,
    val hireDate: LocalDate?
)

data class EmployeeUpdateDto(
    val name: String,
    val email: String,
    val phone: String,
    val roleType: String
)

data class EmployeeTerminationDto(
    val terminationDate: LocalDate?
)

data class ChangeRoleDto(
    val roleType: String
)