package com.fitcore.users.infrastructure.messaging.producer

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component
import com.fitcore.users.domain.model.student.Student
import com.fitcore.users.domain.model.common.UserId
import com.fitcore.users.domain.port.out.student.event.StudentEventPublisher
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import com.fitcore.users.infrastructure.config.messaging.RabbitMQConfig
import com.fitcore.users.infrastructure.web.mapper.StudentDtoMapper
import org.slf4j.LoggerFactory

@Component
class StudentEventProducer(
    private val rabbitTemplate: RabbitTemplate,
    private val studentDtoMapper: StudentDtoMapper
) : StudentEventPublisher {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun publishStudentCreated(student: Student) {
        try {
            val dto = studentDtoMapper.toResponseDto(student)
            rabbitTemplate.convertAndSend(
                RabbitMQConfig.STUDENT_EXCHANGE,
                RabbitMQConfig.STUDENT_ROUTE_KEY,
                dto
            )
            logger.info("Student created event published for: ${student.email}")
        } catch (e: Exception) {
            logger.error("Failed to publish student created event: ${e.message}", e)
            // Implementar estratégia de retry se necessário
        }
    }

    override fun publishStudentPlanChanged(student: Student) {
        val dto = studentDtoMapper.toResponseDto(student)
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.STUDENT_EVENT_EXCHANGE,
            RabbitMQConfig.STUDENT_PLAN_CHANGED_ROUTE_KEY,
            dto
        )
        logger.info("Student plan changed event published for: ${student.email}")
    }

    override fun publishStudentStatusChanged(student: Student) {
        val dto = studentDtoMapper.toResponseDto(student)
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.STUDENT_EVENT_EXCHANGE,
            RabbitMQConfig.STUDENT_STATUS_CHANGED_ROUTE_KEY,
            dto
        )
        logger.info("Student status changed event published for: ${student.email}")
    }

    override fun publishStudentDeleted(studentId: UserId) {
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.STUDENT_EVENT_EXCHANGE,
            RabbitMQConfig.STUDENT_DELETED_ROUTE_KEY,
            mapOf("studentId" to studentId.toString())
        )
        logger.info("Student deleted event published for: $studentId")
    }
}