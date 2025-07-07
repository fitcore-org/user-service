package com.fitcore.users.infrastructure.messaging.producer

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component
import com.fitcore.users.domain.model.student.Student
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
                RabbitMQConfig.RESPONSE_SUCESSO_EXCHANGE,
                RabbitMQConfig.RESPONSE_SUCESSO_ROUTE_KEY,
                dto
            )
        } catch (e: Exception) {
            logger.error("Failed to publish student created event: ${e.message}", e)
            // Implementar estratégia de retry se necessário
        }
    }
}