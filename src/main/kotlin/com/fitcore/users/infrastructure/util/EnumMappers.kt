package com.fitcore.users.infrastructure.util

import com.fitcore.users.domain.model.student.StudentPlan
import com.fitcore.users.domain.model.employee.Role
import com.fitcore.users.infrastructure.persistence.entity.StudentJpaEntity
// import com.fitcore.users.infrastructure.persistence.entity.EmployeeJpaEntity

/**
 * Classe utilitária para centralizar o mapeamento de enums entre
 * camadas de domínio e infraestrutura
 */
object EnumMappers {
    
    // Mapeamentos para StudentPlan
    fun toPlanEntity(plan: StudentPlan): StudentJpaEntity.PlanType {
        return when (plan) {
            StudentPlan.BASIC -> StudentJpaEntity.PlanType.BASIC
            StudentPlan.INTERMEDIATE -> StudentJpaEntity.PlanType.INTERMEDIATE
            StudentPlan.PREMIUM -> StudentJpaEntity.PlanType.PREMIUM
        }
    }
    
    fun toPlanDomain(planType: StudentJpaEntity.PlanType): StudentPlan {
        return when (planType) {
            StudentJpaEntity.PlanType.BASIC -> StudentPlan.BASIC
            StudentJpaEntity.PlanType.INTERMEDIATE -> StudentPlan.INTERMEDIATE
            StudentJpaEntity.PlanType.PREMIUM -> StudentPlan.PREMIUM
        }
    }
    
    fun toPlanDomain(planName: String): StudentPlan {
        return StudentPlan.fromString(planName)
    }
    
    // Mapeamentos para Role (Employee)
    /* fun toRoleEntity(role: Role): String {
        return role.name
    }
    
    fun toRoleDomain(roleName: String): Role {
        return Role.fromString(roleName)
    }
    */
}