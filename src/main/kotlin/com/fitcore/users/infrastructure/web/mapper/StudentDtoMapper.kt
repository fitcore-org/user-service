package com.fitcore.users.infrastructure.web.mapper

import com.fitcore.users.domain.model.student.Student
import com.fitcore.users.infrastructure.web.dto.student.StudentResponseDto
import org.springframework.stereotype.Component

@Component
class StudentDtoMapper {
    
    fun toResponseDto(domain: Student): StudentResponseDto {
        return StudentResponseDto(
            id = domain.id.toString(),
            name = domain.name,
            email = domain.email,
            cpf = domain.cpf,
            birthDate = domain.birthDate,
            phone = domain.phone,
            planType = domain.plan.name,
            planDescription = domain.plan.description,
            weight = domain.weight,
            height = domain.height,
            bmi = domain.calculateBMI(),
            active = domain.active,
            registrationDate = domain.registrationDate
        )
    }
}