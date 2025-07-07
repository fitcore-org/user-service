package com.fitcore.users.infrastructure.persistence.adapter

import com.fitcore.users.domain.model.common.UserId
import com.fitcore.users.domain.model.student.Student
import com.fitcore.users.domain.model.student.StudentPlan
import com.fitcore.users.domain.port.out.student.StudentRepository
import com.fitcore.users.infrastructure.persistence.entity.StudentJpaEntity
import com.fitcore.users.infrastructure.persistence.mapper.StudentPersistenceMapper
import com.fitcore.users.infrastructure.persistence.repository.StudentJpaRepository
import com.fitcore.users.infrastructure.util.EnumMappers
import org.springframework.stereotype.Component

@Component
class StudentPersistenceAdapter(
    private val studentJpaRepository: StudentJpaRepository,
    private val mapper: StudentPersistenceMapper
) : StudentRepository {
    
    override fun save(student: Student): Student {
        val entity = mapper.toEntity(student)
        val savedEntity = studentJpaRepository.save(entity)
        return mapper.toDomain(savedEntity)
    }
    
    override fun findById(id: UserId): Student? {
        return studentJpaRepository.findById(id.value)
            .map { mapper.toDomain(it) }
            .orElse(null)
    }
    
    override fun findByEmail(email: String): Student? {
        return studentJpaRepository.findByEmail(email)?.let { mapper.toDomain(it) }
    }
    
    override fun findByCpf(cpf: String): Student? {
        return studentJpaRepository.findByCpf(cpf)?.let { mapper.toDomain(it) }
    }
    
    override fun findByPlan(plan: StudentPlan): List<Student> {
        return studentJpaRepository.findByPlan(plan).map { mapper.toDomain(it) }
    }
    
    override fun findAllActive(): List<Student> {
        return studentJpaRepository.findByActive(true).map { mapper.toDomain(it) }
    }
    
    override fun findAll(): List<Student> {
        return studentJpaRepository.findAll().map { mapper.toDomain(it) }
    }
    
    override fun deleteById(id: UserId): Boolean {
        return try {
            studentJpaRepository.deleteById(id.value)
            true
        } catch (e: Exception) {
            false
        }
    }
}