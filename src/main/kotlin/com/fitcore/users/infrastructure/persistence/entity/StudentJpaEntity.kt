package com.fitcore.users.infrastructure.persistence.entity

import com.fitcore.users.domain.model.student.StudentPlan 
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "students")
class StudentJpaEntity(
    @Id
    val id: UUID,
    
    @Column(nullable = false)
    val name: String,
    
    @Column(nullable = false, unique = true)
    val email: String,
    
    @Column(nullable = false, unique = true)
    val cpf: String,
    
    @Column(nullable = false)
    val birthDate: LocalDate,
    
    @Column(nullable = false)
    val phone: String,
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val plan: StudentPlan,
    
    @Column
    val weight: Double?,
    
    @Column
    val height: Int?,
    
    @Column(nullable = false)
    val active: Boolean,
    
    @Column(nullable = false)
    val registrationDate: LocalDateTime,
    
    @Column(nullable = false)
    val lastUpdateDate: LocalDateTime,
    
    @Column
    val profileUrl: String?  
)