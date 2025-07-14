package com.fitcore.users.infrastructure.seeding

import com.fitcore.users.domain.port.`in`.student.ManageStudentUseCase
import com.fitcore.users.domain.port.`in`.employee.ManageEmployeeUseCase
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("seed")
class DataSeeder(
    private val manageStudentUseCase: ManageStudentUseCase,
    private val manageEmployeeUseCase: ManageEmployeeUseCase,
    private val userSeedData: UserSeedData
) : CommandLineRunner {
    
    override fun run(vararg args: String?) {
        seedUsers()
    }
    
    private fun seedUsers() {
        println("🌱 Starting user seeding...")
        
        try {
            // Seed Students
            val students = userSeedData.getStudents()
            students.forEach { student ->
                try {
                    manageStudentUseCase.registerStudent(
                        name = student.name,
                        email = student.email,
                        cpf = student.cpf,
                        birthDate = student.birthDate,
                        phone = student.phone,
                        planType = student.plan.name,
                        weight = student.weight,
                        height = student.height
                    )
                } catch (e: Exception) {
                    println("⚠️ Skipping student ${student.email}: ${e.message}")
                }
            }
            
            // Seed Employees
            val employees = userSeedData.getEmployees()
            employees.forEach { employee ->
                try {
                    manageEmployeeUseCase.registerEmployee(
                        name = employee.name,
                        email = employee.email,
                        cpf = employee.cpf,
                        birthDate = employee.birthDate,
                        phone = employee.phone,
                        roleType = employee.role.name,
                        hireDate = employee.hireDate
                    )
                } catch (e: Exception) {
                    println("⚠️ Skipping employee ${employee.email}: ${e.message}")
                }
            }
            
            println("✅ User seeding completed!")
            
        } catch (e: Exception) {
            println("❌ Error during seeding: ${e.message}")
        }
    }
}