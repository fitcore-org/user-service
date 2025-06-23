package com.fitcore.users.domain.port.out.student

import com.fitcore.users.domain.model.student.Student

interface StudentEventPublisher {
    fun publishStudentCreated(student: Student)
    fun publishStudentUpdated(student: Student)
    fun publishStudentDeactivated(student: Student)
    fun publishStudentActivated(student: Student)
}