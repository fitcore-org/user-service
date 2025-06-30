package com.fitcore.users.infrastructure.web.dto.student

import java.time.LocalDate

data class StudentRequestDto(
    val name: String,
    val email: String,
    val cpf: String,
    val birthDate: LocalDate,
    val phone: String,
    val planType: String,
    val weight: Double?,
    val height: Int?
)

data class StudentUpdateDto(
    val name: String,
    val email: String,
    val phone: String,
    val planType: String,
    val weight: Double?,
    val height: Int?
)

data class PhysicalDataUpdateDto(
    val weight: Double?,
    val height: Int?
)