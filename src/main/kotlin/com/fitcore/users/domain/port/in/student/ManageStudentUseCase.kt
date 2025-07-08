package com.fitcore.users.domain.port.`in`.student

import com.fitcore.users.domain.model.common.UserId
import com.fitcore.users.domain.model.student.Student
import com.fitcore.users.domain.model.student.StudentPlan
import java.time.LocalDate

interface ManageStudentUseCase {
    fun registerStudent(
        name: String,
        email: String,
        cpf: String,
        birthDate: LocalDate,
        phone: String,
        planType: String,
        weight: Double?,
        height: Int?
    ): Student
    
    fun updateStudent(
        id: UserId,
        name: String,
        email: String,
        phone: String,
        planType: String,
        weight: Double?,
        height: Int?,
        profileUrl: String? = null
    ): Student
    
    fun updatePhysicalData(
        id: UserId,
        weight: Double?,
        height: Int?
    ): Student
    
    fun activateStudent(id: UserId): Student
    
    fun deactivateStudent(id: UserId): Student
    
    fun deleteStudent(id: UserId): Boolean
}