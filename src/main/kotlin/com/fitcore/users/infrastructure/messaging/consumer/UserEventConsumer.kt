package com.fitcore.users.infrastructure.messaging.consumer

import com.fitcore.users.infrastructure.config.messaging.RabbitMQConfig
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import java.io.Serializable
import java.time.LocalDate
import com.fitcore.users.application.service.StudentService
import com.fitcore.users.domain.model.student.StudentPlan

@Component
class UserEventConsumer(
    private val studentService: StudentService
) {

    private fun formatCpf(cpf: String?): String {
    if (cpf == null) return "000.000.000-00"
    return cpf.replace(Regex("(\\d{3})(\\d{3})(\\d{3})(\\d{2})"), "$1.$2.$3-$4")
    }
    @RabbitListener(queues = [RabbitMQConfig.REQUEST_QUEUE])
    fun handleUserRegisteredEvent(event: UserRegisteredEvent) {
        // Exemplo: se for aluno, cria Student com defaults
        if (event.role.equals("STUDENT", ignoreCase = true)) {
            studentService.registerStudent(
                name = event.name,
                email = event.email,
                cpf = formatCpf(event.cpf),
                birthDate = event.birthDate?.let { LocalDate.parse(it) } ?: LocalDate.of(2000, 1, 1),
                phone = "(00) 00000-0000",
                planType = StudentPlan.BASIC.name,
                weight = null,
                height = null
            )
        }
        // Se quiser tratar EMPLOYEE, adicione lógica aqui
    }
}

// DTO do evento recebido
data class UserRegisteredEvent(
    val id: Long,
    val name: String,
    val email: String,
    val role: String,
    val cpf: String?,
    val birthDate: String?
) : Serializable