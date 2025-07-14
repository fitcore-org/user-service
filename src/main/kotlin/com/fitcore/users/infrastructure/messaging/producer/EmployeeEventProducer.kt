package com.fitcore.users.infrastructure.messaging.producer

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component
import com.fitcore.users.domain.model.employee.Employee
import com.fitcore.users.domain.model.common.UserId
import com.fitcore.users.domain.port.out.employee.event.EmployeeEventPublisher
import com.fitcore.users.infrastructure.config.messaging.RabbitMQConfig
import com.fitcore.users.infrastructure.web.mapper.EmployeeDtoMapper
import org.slf4j.LoggerFactory

@Component
class EmployeeEventProducer(
    private val rabbitTemplate: RabbitTemplate,
    private val employeeDtoMapper: EmployeeDtoMapper
) : EmployeeEventPublisher {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun publishEmployeeCreated(employee: Employee) {
        try {
            val dto = employeeDtoMapper.toResponseDto(employee)
            rabbitTemplate.convertAndSend(
                RabbitMQConfig.EMPLOYEE_EXCHANGE,
                RabbitMQConfig.EMPLOYEE_ROUTE_KEY,
                dto
            )
            logger.info("Employee created event published for: ${employee.email}")
        } catch (e: Exception) {
            logger.error("Failed to publish employee created event for ${employee.email}: ${e.message}", e)
        }
    }

    override fun publishEmployeeUpdated(employee: Employee) {
        //try {
        //    val dto = employeeDtoMapper.toResponseDto(employee)
        //    rabbitTemplate.convertAndSend(
        //        RabbitMQConfig.RESPONSE_SUCESSO_EXCHANGE,
        //        "employee-updated",
        //        dto
        //    )
        //    logger.info("Employee updated event published for: ${employee.email}")
        //} catch (e: Exception) {
        //    logger.error("Failed to publish employee updated event for ${employee.email}: ${e.message}", e)
        //}
    }

    override fun publishEmployeeTerminated(employee: Employee) {
        //try {
        //    val dto = employeeDtoMapper.toResponseDto(employee)
        //    rabbitTemplate.convertAndSend(
        //        RabbitMQConfig.RESPONSE_SUCESSO_EXCHANGE,
        //        "employee-terminated",
        //        dto
        //    )
        //    logger.info("Employee terminated event published for: ${employee.email}")
        //} catch (e: Exception) {
        //    logger.error("Failed to publish employee terminated event for ${employee.email}: ${e.message}", e)
        //}
    }

    override fun publishEmployeeRoleChanged(employee: Employee) {
        val dto = employeeDtoMapper.toResponseDto(employee)
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EMPLOYEE_EVENT_EXCHANGE,
            RabbitMQConfig.EMPLOYEE_ROLE_CHANGED_ROUTE_KEY,
            dto
        )
        logger.info("Employee role changed event published for: ${employee.email}")
    }

    override fun publishEmployeeStatusChanged(employee: Employee) {
        val dto = employeeDtoMapper.toResponseDto(employee)
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EMPLOYEE_EVENT_EXCHANGE,
            RabbitMQConfig.EMPLOYEE_STATUS_CHANGED_ROUTE_KEY,
            dto
        )
        logger.info("Employee status changed event published for: ${employee.email}")
    }

    override fun publishEmployeeDeleted(employeeId: UserId) {
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EMPLOYEE_EVENT_EXCHANGE,
            RabbitMQConfig.EMPLOYEE_DELETED_ROUTE_KEY,
            mapOf("employeeId" to employeeId.toString())
        )
        logger.info("Employee deleted event published for: $employeeId")
    }
}