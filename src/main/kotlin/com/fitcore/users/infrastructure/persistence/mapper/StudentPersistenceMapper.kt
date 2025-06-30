package com.fitcore.users.infrastructure.persistence.mapper

import com.fitcore.users.domain.model.common.UserId
import com.fitcore.users.domain.model.student.Student
import com.fitcore.users.domain.model.student.StudentPlan
import com.fitcore.users.infrastructure.persistence.entity.StudentJpaEntity
import com.fitcore.users.infrastructure.util.EnumMappers
import org.springframework.stereotype.Component

@Component
class StudentPersistenceMapper {
    
    fun toEntity(domain: Student): StudentJpaEntity {
        return StudentJpaEntity(
            id = domain.id.value,
            name = domain.name,
            email = domain.email,
            cpf = domain.cpf,
            birthDate = domain.birthDate,
            phone = domain.phone,
            plan = EnumMappers.toPlanEntity(domain.plan),
            weight = domain.weight,
            height = domain.height,
            active = domain.active,
            registrationDate = domain.registrationDate,
            lastUpdateDate = domain.lastUpdateDate
        )
    }
    
    fun toDomain(entity: StudentJpaEntity): Student {
        return Student.fromPersistence(
            id = entity.id,
            name = entity.name,
            email = entity.email,
            cpf = entity.cpf,
            birthDate = entity.birthDate,
            phone = entity.phone,
            plan = EnumMappers.toPlanDomain(entity.plan),
            weight = entity.weight,
            height = entity.height,
            active = entity.active,
            registrationDate = entity.registrationDate,
            lastUpdateDate = entity.lastUpdateDate
        )
    }
}