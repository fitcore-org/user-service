package com.fitcore.users.infrastructure.web.dto.employee

import java.time.LocalDate
import java.time.LocalDateTime

data class EmployeeResponseDto(
    val id: String,
    val name: String,
    val email: String,
    val cpf: String,
    val birthDate: LocalDate,
    val phone: String,
    val role: String,
    val roleDescription: String,
    val active: Boolean,
    val hireDate: LocalDate,
    val terminationDate: LocalDate?,
    val registrationDate: LocalDateTime
)