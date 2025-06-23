package com.fitcore.users.domain.model.student

enum class StudentPlan(val description: String, val monthlyFee: Double) {
    BASIC("Acesso básico à academia", 89.90),
    INTERMEDIATE("Acesso intermediário com algumas aulas", 129.90),
    PREMIUM("Acesso completo a todas as aulas e serviços", 189.90);

    companion object {
        fun fromString(planName: String): StudentPlan {
            return try {
                valueOf(planName.uppercase())
            } catch (e: IllegalArgumentException) {
                throw IllegalArgumentException("Invalid plan type: $planName. Valid values are: ${values().joinToString()}")
            }
        }
    }
}