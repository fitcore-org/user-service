package com.fitcore.users.infrastructure.persistence.repository

import com.fitcore.users.domain.model.employee.Role
import com.fitcore.users.infrastructure.persistence.entity.EmployeeJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface EmployeeJpaRepository : JpaRepository<EmployeeJpaEntity, UUID> {
    fun findByEmail(email: String): EmployeeJpaEntity?
    fun findByCpf(cpf: String): EmployeeJpaEntity?
    fun findByActive(active: Boolean): List<EmployeeJpaEntity>
    fun findByRole(role: Role): List<EmployeeJpaEntity>
}