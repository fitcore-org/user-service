package com.fitcore.users.domain.port.`in`.student

import com.fitcore.users.domain.model.common.UserId
import com.fitcore.users.domain.model.student.Student

interface FindStudentUseCase {
    fun findById(id: UserId): Student?
    fun findByEmail(email: String): Student?
    fun findByCpf(cpf: String): Student?
    fun findByPlan(planType: String): List<Student>
    fun findAllActive(): List<Student>
    fun findAll(): List<Student>
}