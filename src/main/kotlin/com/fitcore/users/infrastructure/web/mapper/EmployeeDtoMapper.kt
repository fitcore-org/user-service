package com.fitcore.users.infrastructure.web.mapper

import com.fitcore.users.domain.model.employee.Employee
import com.fitcore.users.infrastructure.web.dto.employee.EmployeeResponseDto
import com.fitcore.users.infrastructure.service.StorageService
import org.springframework.stereotype.Component

@Component
class EmployeeDtoMapper(
    private val storageService: StorageService
) {
    
    fun toResponseDto(domain: Employee): EmployeeResponseDto {
        val url = domain.profileUrl?.let { storageService.getPresignedUrl(it) }
        return EmployeeResponseDto(
            id = domain.id.toString(),
            name = domain.name,
            email = domain.email,
            cpf = domain.cpf,
            birthDate = domain.birthDate,
            phone = domain.phone,
            role = domain.role.name,
            roleDescription = domain.role.description,
            active = domain.active,
            hireDate = domain.hireDate,
            terminationDate = domain.terminationDate,
            registrationDate = domain.registrationDate,
            profileUrl = url
        )
    }
}