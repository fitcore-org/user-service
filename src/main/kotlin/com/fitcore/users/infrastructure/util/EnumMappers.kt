package com.fitcore.users.infrastructure.util

import com.fitcore.users.domain.model.student.StudentPlan
import com.fitcore.users.domain.model.employee.Role

object EnumMappers {
    // Apenas conversão String <-> Enum, se necessário
    fun toPlanDomain(planName: String): StudentPlan {
        return StudentPlan.fromString(planName)
    }

    fun toRoleEntity(role: Role): String {
        return role.name
    }

    fun toRoleDomain(roleName: String): Role {
        return Role.fromString(roleName)
    }
}