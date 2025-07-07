package com.fitcore.users.infrastructure.persistence.mapper

import com.fitcore.users.domain.model.employee.Employee
import com.fitcore.users.infrastructure.persistence.entity.EmployeeJpaEntity
import com.fitcore.users.infrastructure.util.EnumMappers
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.UUID

@Component
class EmployeePersistenceMapper {
    
    fun toEntity(domain: Employee): EmployeeJpaEntity {
        return EmployeeJpaEntity(
            id = domain.id.value,
            name = domain.name,
            email = domain.email,
            cpf = domain.cpf,
            birthDate = domain.birthDate,
            phone = domain.phone,
            role = domain.role,
            active = domain.active,
            hireDate = domain.hireDate,
            terminationDate = domain.terminationDate,
            registrationDate = domain.registrationDate,
            lastUpdateDate = domain.lastUpdateDate
        )
    }
    
    fun toDomain(entity: EmployeeJpaEntity): Employee {
        return Employee.fromPersistence(
            id = entity.id,
            name = entity.name,
            email = entity.email,
            cpf = entity.cpf,
            birthDate = entity.birthDate,
            phone = entity.phone,
            role = entity.role,
            active = entity.active,
            hireDate = entity.hireDate,
            terminationDate = entity.terminationDate,
            registrationDate = entity.registrationDate,
            lastUpdateDate = entity.lastUpdateDate
        )
    }
}