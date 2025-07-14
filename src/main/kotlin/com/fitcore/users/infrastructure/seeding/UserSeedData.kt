package com.fitcore.users.infrastructure.seeding

import com.fitcore.users.domain.model.student.Student
import com.fitcore.users.domain.model.student.StudentPlan
import com.fitcore.users.domain.model.employee.Employee
import com.fitcore.users.domain.model.employee.Role
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class UserSeedData {
    
    fun getStudents(): List<Student> {
        val students = mutableListOf<Student>()
        
        // 10 Students
        repeat(10) { i ->
            students.add(Student.create(
                name = "Student ${i + 1}",
                email = "student${i + 1}@fitcore.com",
                cpf = "123.456.789-${String.format("%02d", i + 1)}",
                birthDate = LocalDate.of(1995 + i, (i % 12) + 1, (i % 28) + 1),
                phone = "(11) 99999-${String.format("%04d", 1000 + i)}",
                plan = if (i % 2 == 0) StudentPlan.BASIC else StudentPlan.PREMIUM,
                weight = 60.0 + (i * 5),
                height = 160 + (i * 2)
            ))
        }
        
        return students
    }
    
    fun getEmployees(): List<Employee> {
        val employees = mutableListOf<Employee>()
        
        // 2 Managers
        repeat(2) { i ->
            employees.add(Employee.create(
                name = "Manager ${i + 1}",
                email = "manager${i + 1}@fitcore.com",
                cpf = "111.222.333-${String.format("%02d", i + 11)}",
                birthDate = LocalDate.of(1980 + i, (i % 12) + 1, (i % 28) + 1),
                phone = "(11) 98888-${String.format("%04d", 1000 + i)}",
                role = Role.MANAGER,
                hireDate = LocalDate.now().minusYears(2 + i.toLong())
            ))
        }
        
        // 2 Secretaries (RECEPTIONIST no seu enum)
        repeat(2) { i ->
            employees.add(Employee.create(
                name = "Secretary ${i + 1}",
                email = "secretary${i + 1}@fitcore.com",
                cpf = "222.333.444-${String.format("%02d", i + 13)}",
                birthDate = LocalDate.of(1985 + i, (i % 12) + 1, (i % 28) + 1),
                phone = "(11) 97777-${String.format("%04d", 1000 + i)}",
                role = Role.RECEPTIONIST,
                hireDate = LocalDate.now().minusYears(1 + i.toLong())
            ))
        }
        
        // 6 Instructors
        repeat(6) { i ->
            employees.add(Employee.create(
                name = "Instructor ${i + 1}",
                email = "instructor${i + 1}@fitcore.com",
                cpf = "333.444.555-${String.format("%02d", i + 15)}",
                birthDate = LocalDate.of(1990 + i, (i % 12) + 1, (i % 28) + 1),
                phone = "(11) 96666-${String.format("%04d", 1000 + i)}",
                role = Role.INSTRUCTOR,
                hireDate = LocalDate.now().minusMonths(6 + i.toLong())
            ))
        }
        
        return employees
    }
}