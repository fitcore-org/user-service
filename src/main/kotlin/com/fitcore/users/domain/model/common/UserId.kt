package com.fitcore.users.domain.model.common

import java.util.UUID

data class UserId private constructor(val value: UUID) {

    companion object {
        fun create(): UserId = UserId(UUID.randomUUID())
        
        fun of(id: String): UserId {
            return try {
                UserId(UUID.fromString(id))
            } catch (e: IllegalArgumentException) {
                throw IllegalArgumentException("Invalid user ID format: '$id'") 
            }
        }
        
        fun from(id: UUID): UserId = UserId(id)
    }
    
    override fun toString(): String = value.toString() 
}