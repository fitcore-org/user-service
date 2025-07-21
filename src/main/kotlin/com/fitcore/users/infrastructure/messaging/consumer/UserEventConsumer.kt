package com.fitcore.users.infrastructure.messaging.consumer

import com.fitcore.users.infrastructure.config.messaging.RabbitMQConfig
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import java.io.Serializable
import java.time.LocalDate
import com.fitcore.users.application.service.StudentService
import com.fitcore.users.application.service.EmployeeService
import com.fitcore.users.domain.model.student.StudentPlan
import com.fitcore.users.domain.model.employee.Role
import org.slf4j.LoggerFactory

@Component
class UserEventConsumer(
    private val studentService: StudentService,
    private val employeeService: EmployeeService
) {
    
    private val logger = LoggerFactory.getLogger(javaClass)

    private fun formatCpf(cpf: String?): String {
        if (cpf == null) return "000.000.000-00"
        return cpf.replace(Regex("(\\d{3})(\\d{3})(\\d{3})(\\d{2})"), "$1.$2.$3-$4")
    }
    
    private fun formatPhone(phone: String?): String {
        return phone ?: "(00) 00000-0000"
    }
    
    private fun mapRoleToEmployee(role: String): Role {
        return when (role.uppercase()) {
            "ADMIN" -> Role.ADMIN
            "SECRETARY" -> Role.RECEPTIONIST
            "TEACHER" -> Role.INSTRUCTOR
            "MANAGER" -> Role.MANAGER
            else -> Role.RECEPTIONIST // Default para roles não mapeados
        }
    }
    
    @RabbitListener(queues = [RabbitMQConfig.REQUEST_QUEUE])
    fun handleUserRegisteredEvent(event: UserRegisteredEvent) {
        try {
            when (event.role.uppercase()) {
                "STUDENT" -> {
                    logger.info("Processing student registration for email: ${event.email}")
                    studentService.registerStudent(
                        name = event.name,
                        email = event.email,
                        cpf = formatCpf(event.cpf),
                        birthDate = event.birthDate?.let { LocalDate.parse(it) } ?: LocalDate.of(2000, 1, 1),
                        phone = formatPhone(event.phone),
                        planType = StudentPlan.BASIC.name,
                        weight = null,
                        height = null
                    )
                }
                "ADMIN", "SECRETARY", "TEACHER", "MANAGER" -> {
                    logger.info("Processing employee registration for email: ${event.email}, role: ${event.role}")
                    val employeeRole = mapRoleToEmployee(event.role)
                    
                    employeeService.registerEmployee(
                        name = event.name,
                        email = event.email,
                        cpf = formatCpf(event.cpf),
                        birthDate = event.birthDate?.let { LocalDate.parse(it) } ?: LocalDate.of(1990, 1, 1),
                        phone = formatPhone(event.phone),
                        roleType = employeeRole.name,
                        hireDate = LocalDate.now()
                    )
                }
                else -> {
                    logger.warn("Unknown role received: ${event.role} for user: ${event.email}")
                }
            }
        } catch (e: Exception) {
            logger.error("Failed to process user registration event for ${event.email}: ${e.message}", e)
            // Aqui você pode implementar uma estratégia de retry ou dead letter queue
        }
    }
}

// DTO do evento recebido - atualizado com phone
data class UserRegisteredEvent(
    val id: Long,
    val name: String,
    val email: String,
    val role: String,
    val cpf: String?,
    val birthDate: String?,
    val phone: String?
) : Serializable