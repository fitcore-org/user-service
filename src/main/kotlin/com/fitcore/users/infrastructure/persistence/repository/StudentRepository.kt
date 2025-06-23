package com.fitcore.users.infrastructure.persistence.repository

import com.fitcore.users.infrastructure.persistence.entity.StudentJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface StudentJpaRepository : JpaRepository<StudentJpaEntity, UUID> {
    fun findByEmail(email: String): StudentJpaEntity?
    fun findByCpf(cpf: String): StudentJpaEntity?
    fun findByActive(active: Boolean): List<StudentJpaEntity>
    fun findByPlan(plan: StudentJpaEntity.PlanType): List<StudentJpaEntity>
}