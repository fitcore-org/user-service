package com.fitcore.users.domain.port.out.student

import com.fitcore.users.domain.model.common.UserId
import com.fitcore.users.domain.model.student.Student

interface StudentRepository {
    fun save(student: Student): Student
    fun findById(id: UserId): Student?
    fun findByEmail(email: String): Student?
    fun findByCpf(cpf: String): Student?
    fun findByPlan(plan: String): List<Student>
    fun findAllActive(): List<Student>
    fun findAll(): List<Student>
    fun deleteById(id: UserId): Boolean
}