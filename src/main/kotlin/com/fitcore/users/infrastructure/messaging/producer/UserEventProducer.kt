package com.fitcore.users.infrastructure.messaging.producer

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import com.fitcore.users.infrastructure.config.messaging.RabbitMQConfig
import com.fitcore.users.infrastructure.web.dto.student.StudentResponseDto

@Component
class UserEventProducer(
    private val rabbitTemplate: RabbitTemplate
) {
    fun publishStudentCreated(student: StudentResponseDto) {
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.RESPONSE_SUCESSO_EXCHANGE,
            RabbitMQConfig.RESPONSE_SUCESSO_ROUTE_KEY,
            student
        )
    }
}