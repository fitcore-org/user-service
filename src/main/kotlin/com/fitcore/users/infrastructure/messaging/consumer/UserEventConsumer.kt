package com.fitcore.users.infrastructure.messaging.consumer

import com.fitcore.users.infrastructure.config.messaging.RabbitMQConfig
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import java.io.Serializable
import java.time.LocalDate
import com.fitcore.users.application.service.StudentService
import com.fitcore.users.application.service.EmployeeService
import com.fitcore.users.domain.model.employee.Role
import com.fitcore.users.domain.model.common.UserId
import org.slf4j.LoggerFactory
import java.util.UUID

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
            "SECRETARY" -> Role.RECEPTIONIST
            "TEACHER" -> Role.PERSONAL_TRAINER
            "MANAGER" -> Role.MANAGER
            else -> Role.CLEANER // Default para roles não mapeados
        }
    }
    
    //@RabbitListener(queues = [RabbitMQConfig.REQUEST_QUEUE])
    //fun handleUserRegisteredEvent(event: UserRegisteredEvent) {
    //    try {
    //        when (event.role.uppercase()) {
    //            "STUDENT" -> {
    //                logger.info("Processing student registration for email: ${event.email}")
    //                studentService.registerStudent(
    //                    name = event.name,
    //                    email = event.email,
    //                    cpf = formatCpf(event.cpf),
    //                    birthDate = event.birthDate?.let { LocalDate.parse(it) } ?: LocalDate.of(2000, 1, 1),
    //                    phone = formatPhone(event.phone),
    //                    planType = "BASIC",
    //                    weight = null,
    //                    height = null
    //                )
    //            }
    //            "ADMIN", "SECRETARY", "TEACHER", "MANAGER" -> {
    //                logger.info("Processing employee registration for email: ${event.email}, role: $//{event.role}")
    //                val employeeRole = mapRoleToEmployee(event.role)
    //                
    //                employeeService.registerEmployee(
    //                    name = event.name,
    //                    email = event.email,
    //                    cpf = formatCpf(event.cpf),
    //                    birthDate = event.birthDate?.let { LocalDate.parse(it) } ?: LocalDate.of(1990, 1, 1),
    //                    phone = formatPhone(event.phone),
    //                    roleType = employeeRole.name,
    //                    hireDate = LocalDate.now()
    //                )
    //            }
    //            else -> {
    //                logger.warn("Unknown role received: ${event.role} for user: ${event.email}")
    //            }
    //        }
    //    } catch (e: Exception) {
    //        logger.error("Failed to process user registration event for ${event.email}: ${e.message}", e)
    //        // Aqui você pode implementar uma estratégia de retry ou dead letter queue
    //    }
    //}
    
    @RabbitListener(queues = [RabbitMQConfig.USER_EMPLOYEE_STATUS_CHANGED_QUEUE])
    fun handleEmployeeStatusChangedEvent(event: EmployeeStatusChangedEvent) {
        try {
            logger.info("Processing employee status change for ID: ${event.id}, active: ${event.active}")
            
            val employeeId = UserId.from(UUID.fromString(event.id))
            employeeService.updateEmployeeStatus(employeeId, event.active)
            
            logger.info("Successfully updated employee status for ID: ${event.id}")
        } catch (e: Exception) {
            logger.error("Failed to process employee status change event for ID ${event.id}: ${e.message}", e)
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

// DTO para o evento de alteração de status do employee
data class EmployeeStatusChangedEvent(
    val id: String,  // UUID do employee
    val active: Boolean
) : Serializable