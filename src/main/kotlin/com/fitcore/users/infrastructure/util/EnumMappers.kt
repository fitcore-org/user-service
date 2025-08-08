package com.fitcore.users.infrastructure.util

import com.fitcore.users.domain.model.employee.Role

object EnumMappers {
    fun toRoleEntity(role: Role): String {
        return role.name
    }

    fun toRoleDomain(roleName: String): Role {
        return Role.fromString(roleName)
    }
}