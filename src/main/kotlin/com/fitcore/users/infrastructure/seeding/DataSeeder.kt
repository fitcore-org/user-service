package com.fitcore.users.infrastructure.seeding

import com.fitcore.users.domain.port.`in`.student.ManageStudentUseCase
import com.fitcore.users.domain.port.`in`.employee.ManageEmployeeUseCase
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataSeeder(
    private val manageStudentUseCase: ManageStudentUseCase,
    private val manageEmployeeUseCase: ManageEmployeeUseCase,
    private val userSeedData: UserSeedData,
    @Value("\${user.seeding.enabled:false}") private val seedingEnabled: Boolean
) : CommandLineRunner {
    
    private val logger = LoggerFactory.getLogger(javaClass)
    
    override fun run(vararg args: String?) {
        if (!seedingEnabled) {
            logger.info("🚫 Seeding DESABILITADO - para habilitar, defina USER_SEEDING_ENABLED=true")
            return
        }
        
        logger.info("🌱 Seeding HABILITADO - iniciando população do banco de dados...")
        seedUsers()
    }
    
    private fun seedUsers() {
        logger.info("🌱 Iniciando seeding de usuários...")
        
        try {
            // Seed Students
            val students = userSeedData.getStudents()
            logger.info("📚 Populando ${students.size} estudantes...")
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
                        height = student.height,
                        registrationDate = student.registrationDate
                    )
                } catch (e: Exception) {
                    logger.warn("⚠️ Pulando estudante ${student.email}: ${e.message}")
                }
            }
            
            // Seed Employees
            val employees = userSeedData.getEmployees()
            logger.info("👥 Populando ${employees.size} funcionários...")
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
                    logger.warn("⚠️ Pulando funcionário ${employee.email}: ${e.message}")
                }
            }
            
            logger.info("✅ Seeding de usuários concluído com sucesso!")
            
        } catch (e: Exception) {
            logger.error("❌ Erro durante o seeding: ${e.message}", e)
        }
    }
}