package com.fitcore.users.infrastructure.persistence.mapper

import com.fitcore.users.domain.model.common.UserId
import com.fitcore.users.domain.model.student.Student
import com.fitcore.users.domain.model.student.StudentPlan
import com.fitcore.users.infrastructure.persistence.entity.StudentJpaEntity
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
            plan = mapToPlanType(domain.plan),
            weight = domain.weight,
            height = domain.height,
            active = domain.active,
            registrationDate = domain.registrationDate,
            lastUpdateDate = domain.lastUpdateDate
        )
    }
    
    fun toDomain(entity: StudentJpaEntity): Student {
        
        val student = Student.create(
            name = entity.name,
            email = entity.email,
            cpf = entity.cpf,
            birthDate = entity.birthDate,
            phone = entity.phone,
            plan = mapToDomainPlan(entity.plan),
            weight = entity.weight,
            height = entity.height
        )
        
        // Reflexão para acessar o construtor privado ou implementar método específico para
        // reconstruir a entidade a partir do banco
        // Isso é uma simplificação para o exemplo
        return student
    }
    
    private fun mapToPlanType(plan: StudentPlan): StudentJpaEntity.PlanType {
        return when (plan) {
            StudentPlan.BASIC -> StudentJpaEntity.PlanType.BASIC
            StudentPlan.INTERMEDIATE -> StudentJpaEntity.PlanType.INTERMEDIATE
            StudentPlan.PREMIUM -> StudentJpaEntity.PlanType.PREMIUM
        }
    }
    
    private fun mapToDomainPlan(planType: StudentJpaEntity.PlanType): StudentPlan {
        return when (planType) {
            StudentJpaEntity.PlanType.BASIC -> StudentPlan.BASIC
            StudentJpaEntity.PlanType.INTERMEDIATE -> StudentPlan.INTERMEDIATE
            StudentJpaEntity.PlanType.PREMIUM -> StudentPlan.PREMIUM
        }
    }
}