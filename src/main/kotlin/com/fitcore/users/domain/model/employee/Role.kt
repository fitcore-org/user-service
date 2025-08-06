package com.fitcore.users.domain.model.employee

enum class Role(val description: String) {
    MANAGER("Gerente/Proprietário da academia"),
    PERSONAL_TRAINER("Personal Trainer especializado"),
    RECEPTIONIST("Recepcionista da academia"),
    CLEANER("Funcionário de limpeza");
    
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