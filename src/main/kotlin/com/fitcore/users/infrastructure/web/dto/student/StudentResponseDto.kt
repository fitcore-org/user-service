package com.fitcore.users.infrastructure.web.dto.student

import java.time.LocalDate
import java.time.LocalDateTime

data class StudentResponseDto(
    val id: String,
    val name: String,
    val email: String,
    val cpf: String,
    val birthDate: LocalDate,
    val phone: String,
    val planType: String,
    val planDescription: String,
    val weight: Double?,
    val height: Int?,
    val bmi: Double?,
    val active: Boolean,
    val registrationDate: LocalDateTime
)