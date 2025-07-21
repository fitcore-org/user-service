package com.fitcore.users.domain.model.employee

import com.fitcore.users.domain.model.common.UserId
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

class Employee private constructor(
    val id: UserId,
    val name: String,
    val email: String,
    val cpf: String,
    val birthDate: LocalDate,
    val phone: String,
    val role: Role,
    val active: Boolean,
    val hireDate: LocalDate,
    val terminationDate: LocalDate?,
    val registrationDate: LocalDateTime,
    val lastUpdateDate: LocalDateTime,
    val profileUrl: String?
) {
    companion object {
        private val CPF_REGEX = Regex("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$")
        private val EMAIL_REGEX = Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
        private val PHONE_REGEX = Regex("^\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}$")
        
        fun fromPersistence(
            id: UUID,
            name: String,
            email: String,
            cpf: String,
            birthDate: LocalDate,
            phone: String,
            role: Role,
            active: Boolean,
            hireDate: LocalDate,
            terminationDate: LocalDate?,
            registrationDate: LocalDateTime,
            lastUpdateDate: LocalDateTime,
            profileUrl: String?
        ): Employee {
            return Employee(
                id = UserId.from(id),
                name = name,
                email = email,
                cpf = cpf,
                birthDate = birthDate,
                phone = phone,
                role = role,
                active = active,
                hireDate = hireDate,
                terminationDate = terminationDate,
                registrationDate = registrationDate,
                lastUpdateDate = lastUpdateDate,
                profileUrl = profileUrl
            )
        }
        
        fun create(
            name: String,
            email: String,
            cpf: String,
            birthDate: LocalDate,
            phone: String,
            role: Role,
            hireDate: LocalDate = LocalDate.now()
        ): Employee {
            // Validações de domínio
            require(name.isNotBlank()) { "Name cannot be blank" }
            require(email.matches(EMAIL_REGEX)) { "Invalid email format" }
            require(cpf.matches(CPF_REGEX)) { "Invalid CPF format" }
            require(phone.matches(PHONE_REGEX)) { "Invalid phone format" }
            require(birthDate.isBefore(LocalDate.now().minusYears(18))) { "Employee must be at least 18 years old" }
            require(!hireDate.isAfter(LocalDate.now())) { "Hire date cannot be in the future" }
            
            val now = LocalDateTime.now()
            
            return Employee(
                id = UserId.create(),
                name = name,
                email = email,
                cpf = cpf,
                birthDate = birthDate,
                phone = phone,
                role = role,
                active = true,
                hireDate = hireDate,
                terminationDate = null,
                registrationDate = now,
                lastUpdateDate = now,
                profileUrl = null 
            )
        }
    }

    fun withProfileUrl(profileUrl: String?): Employee {
        return Employee(
            id = this.id,
            name = this.name,
            email = this.email,
            cpf = this.cpf,
            birthDate = this.birthDate,
            phone = this.phone,
            role = this.role,
            active = this.active,
            hireDate = this.hireDate,
            terminationDate = this.terminationDate,
            registrationDate = this.registrationDate,
            lastUpdateDate = this.lastUpdateDate,
            profileUrl = profileUrl 
        )
    }
    
    fun update(
        name: String,
        email: String,
        phone: String,
        role: Role
    ): Employee {
        // Validações de domínio
        require(name.isNotBlank()) { "Name cannot be blank" }
        require(email.matches(EMAIL_REGEX)) { "Invalid email format" }
        require(phone.matches(PHONE_REGEX)) { "Invalid phone format" }
        
        return Employee(
            id = this.id,
            name = name,
            email = email,
            cpf = this.cpf,
            birthDate = this.birthDate,
            phone = phone,
            role = role,
            active = this.active,
            hireDate = this.hireDate,
            terminationDate = this.terminationDate,
            registrationDate = this.registrationDate,
            lastUpdateDate = LocalDateTime.now(),
            profileUrl = this.profileUrl
        )
    }
    
    fun terminate(terminationDate: LocalDate = LocalDate.now()): Employee {
        require(!terminationDate.isAfter(LocalDate.now())) { "Termination date cannot be in the future" }
        require(terminationDate.isAfter(this.hireDate)) { "Termination date must be after hire date" }
        
        if (!this.active) {
            return this
        }
        
        return Employee(
            id = this.id,
            name = this.name,
            email = this.email,
            cpf = this.cpf,
            birthDate = this.birthDate,
            phone = this.phone,
            role = this.role,
            active = false,
            hireDate = this.hireDate,
            terminationDate = terminationDate,
            registrationDate = this.registrationDate,
            lastUpdateDate = LocalDateTime.now(),
            profileUrl = this.profileUrl
        )
    }
    
    fun reactivate(): Employee {
        if (this.active) {
            return this
        }
        
        return Employee(
            id = this.id,
            name = this.name,
            email = this.email,
            cpf = this.cpf,
            birthDate = this.birthDate,
            phone = this.phone,
            role = this.role,
            active = true,
            hireDate = this.hireDate,
            terminationDate = null,
            registrationDate = this.registrationDate,
            lastUpdateDate = LocalDateTime.now(),
            profileUrl = this.profileUrl
        )
    }
}