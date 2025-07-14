package com.fitcore.users.domain.port.out.student.event

import com.fitcore.users.domain.model.student.Student
import com.fitcore.users.domain.model.common.UserId

interface StudentEventPublisher {
    fun publishStudentCreated(student: Student)
    // fun publishStudentUpdated(student: Student)
    // fun publishStudentDeactivated(student: Student)
    // fun publishStudentActivated(student: Student)

    fun publishStudentPlanChanged(student: Student)
    fun publishStudentStatusChanged(student: Student)
    fun publishStudentDeleted(studentId: UserId)
}