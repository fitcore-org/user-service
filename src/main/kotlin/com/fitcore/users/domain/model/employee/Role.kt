package com.fitcore.users.domain.model.employee

enum class Role(val description: String) {
    ADMIN("Administrador com acesso total ao sistema"),
    MANAGER("Gerente com acesso administrativo limitado"),
    INSTRUCTOR("Instrutor com acesso apenas às suas turmas e alunos"),
    RECEPTIONIST("Recepcionista com acesso limitado ao cadastro e atendimento");
    
    companion object {
        fun fromString(roleName: String): Role {
            return try {
                valueOf(roleName.uppercase())
            } catch (e: IllegalArgumentException) {
                throw IllegalArgumentException("Invalid role: $roleName. Valid values are: ${values().joinToString()}")
            }
        }
    }
}