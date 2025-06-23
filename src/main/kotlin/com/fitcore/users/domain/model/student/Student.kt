package com.fitcore.users.domain.model.student

import com.fitcore.users.domain.model.common.UserId
import java.time.LocalDate
import java.time.LocalDateTime

class Student private constructor(
    val id: UserId,
    val name: String,
    val email: String,
    val cpf: String,
    val birthDate: LocalDate,
    val phone: String,
    val plan: StudentPlan,
    val weight: Double?,         // Peso em kg (opcional)
    val height: Int?,            // Altura em cm (opcional)
    val active: Boolean,
    val registrationDate: LocalDateTime,
    val lastUpdateDate: LocalDateTime
) {
    companion object {
        private val CPF_REGEX = Regex("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$")
        private val EMAIL_REGEX = Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
        private val PHONE_REGEX = Regex("^\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}$")
        
        fun create(
            name: String,
            email: String,
            cpf: String,
            birthDate: LocalDate,
            phone: String,
            plan: StudentPlan,
            weight: Double? = null,   
            height: Int? = null
        ): Student {
            // Validações de domínio
            require(name.isNotBlank()) { "Name cannot be blank" }
            require(email.matches(EMAIL_REGEX)) { "Invalid email format" }
            require(cpf.matches(CPF_REGEX)) { "Invalid CPF format" }
            require(phone.matches(PHONE_REGEX)) { "Invalid phone format" }
            require(birthDate.isBefore(LocalDate.now().minusYears(12))) { "Student must be at least 12 years old" }

            // Validação para peso e altura
            weight?.let { 
                require(it > 0) { "Weight must be positive" }
            }
            
            height?.let {
                require(it > 0) { "Height must be positive" }
            }
            
            val now = LocalDateTime.now()
            
            return Student(
                id = UserId.create(),
                name = name,
                email = email,
                cpf = cpf,
                birthDate = birthDate,
                phone = phone,
                plan = plan,
                weight = weight,
                height = height,
                active = true,
                registrationDate = now,
                lastUpdateDate = now
            )
        }
    }
    
    fun update(
        name: String,
        email: String,
        phone: String,
        plan: StudentPlan,
        weight: Double? = this.weight,
        height: Int? = this.height
    ): Student {
        // Validações de domínio
        require(name.isNotBlank()) { "Name cannot be blank" }
        require(email.matches(EMAIL_REGEX)) { "Invalid email format" }
        require(phone.matches(PHONE_REGEX)) { "Invalid phone format" }

        // Validações para peso e altura
        weight?.let { 
            require(it > 0) { "Weight must be positive" }
        }
        
        height?.let {
            require(it > 0) { "Height must be positive" }
        }
        
        return Student(
            id = this.id,
            name = name,
            email = email,
            cpf = this.cpf,
            birthDate = this.birthDate,
            phone = phone,
            plan = plan,
            weight = weight,
            height = height,
            active = this.active,
            registrationDate = this.registrationDate,
            lastUpdateDate = LocalDateTime.now()
        )
    }
    
    fun updatePhysicalData(weight: Double?, height: Int?): Student {
        // Validações para peso e altura
        weight?.let { 
            require(it > 0) { "Weight must be positive" }
            require(it < 300) { "Weight must be less than 300kg" }
        }
        
        height?.let {
            require(it > 0) { "Height must be positive" }
            require(it in 100..250) { "Height must be between 100cm and 250cm" }
        }
        
        return Student(
            id = this.id,
            name = this.name,
            email = this.email,
            cpf = this.cpf,
            birthDate = this.birthDate,
            phone = this.phone,
            plan = this.plan,
            weight = weight,
            height = height,
            active = this.active,
            registrationDate = this.registrationDate,
            lastUpdateDate = LocalDateTime.now()
        )
    }
    
    fun calculateBMI(): Double? {
        if (weight == null || height == null || height == 0) return null
        
        // IMC = peso (kg) / (altura (m))²
        val heightInMeters = height.toDouble() / 100
        return weight / (heightInMeters * heightInMeters)
    }
    
    fun deactivate(): Student {
        if (!this.active) {
            return this
        }
        
        return Student(
            id = this.id,
            name = this.name,
            email = this.email,
            cpf = this.cpf,
            birthDate = this.birthDate,
            phone = this.phone,
            plan = this.plan,
            weight = this.weight,
            height = this.height,
            active = false,
            registrationDate = this.registrationDate,
            lastUpdateDate = LocalDateTime.now()
        )
    }
    
    fun activate(): Student {
        if (this.active) {
            return this
        }
        
        return Student(
            id = this.id,
            name = this.name,
            email = this.email,
            cpf = this.cpf,
            birthDate = this.birthDate,
            phone = this.phone,
            plan = this.plan,
            weight = this.weight,
            height = this.height,
            active = true,
            registrationDate = this.registrationDate,
            lastUpdateDate = LocalDateTime.now()
        )
    }
}