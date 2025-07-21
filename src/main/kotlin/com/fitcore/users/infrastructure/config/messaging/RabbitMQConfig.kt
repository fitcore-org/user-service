package com.fitcore.users.infrastructure.config.messaging

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {
    companion object {
        // Exchanges
        const val REQUEST_EXCHANGE = "cadastro-usuario-request-exchange"
        const val STUDENT_EXCHANGE = "cadastro-aluno-exchange"
        const val EMPLOYEE_EXCHANGE = "cadastro-funcionario-exchange"

        const val STUDENT_EVENT_EXCHANGE = "student-event-exchange"
        const val EMPLOYEE_EVENT_EXCHANGE = "employee-event-exchange"

        // Queues
        const val REQUEST_QUEUE = "user.registered"
        const val STUDENT_QUEUE = "cadastro-aluno-queue"
        const val EMPLOYEE_QUEUE = "cadastro-funcionario-queue"

        const val STUDENT_PLAN_CHANGED_QUEUE = "student-plan-changed-queue"
        const val STUDENT_STATUS_CHANGED_QUEUE = "student-status-changed-queue"
        const val STUDENT_DELETED_QUEUE = "student-deleted-queue"
        const val EMPLOYEE_ROLE_CHANGED_QUEUE = "employee-role-changed-queue"
        const val EMPLOYEE_STATUS_CHANGED_QUEUE = "employee-status-changed-queue"
        const val EMPLOYEE_DELETED_QUEUE = "employee-deleted-queue"

        // Routing Keys
        const val REQUEST_ROUTE_KEY = "cadastro-usuario-request-route-key"
        const val STUDENT_ROUTE_KEY = "cadastro-aluno-route-key"
        const val EMPLOYEE_ROUTE_KEY = "cadastro-funcionario-route-key"

        const val STUDENT_PLAN_CHANGED_ROUTE_KEY = "student.plan.changed"
        const val STUDENT_STATUS_CHANGED_ROUTE_KEY = "student.status.changed"
        const val STUDENT_DELETED_ROUTE_KEY = "student.deleted"
        const val EMPLOYEE_ROLE_CHANGED_ROUTE_KEY = "employee.role.changed"
        const val EMPLOYEE_STATUS_CHANGED_ROUTE_KEY = "employee.status.changed"
        const val EMPLOYEE_DELETED_ROUTE_KEY = "employee.deleted"
    }

    // Queues
    @Bean fun requestQueue() = Queue(REQUEST_QUEUE, true)
    @Bean fun studentQueue() = Queue(STUDENT_QUEUE, true)
    @Bean fun employeeQueue() = Queue(EMPLOYEE_QUEUE, true)

    @Bean fun studentPlanChangedQueue() = Queue(STUDENT_PLAN_CHANGED_QUEUE, true)
    @Bean fun studentStatusChangedQueue() = Queue(STUDENT_STATUS_CHANGED_QUEUE, true)
    @Bean fun studentDeletedQueue() = Queue(STUDENT_DELETED_QUEUE, true)
    @Bean fun employeeRoleChangedQueue() = Queue(EMPLOYEE_ROLE_CHANGED_QUEUE, true)
    @Bean fun employeeStatusChangedQueue() = Queue(EMPLOYEE_STATUS_CHANGED_QUEUE, true)
    @Bean fun employeeDeletedQueue() = Queue(EMPLOYEE_DELETED_QUEUE, true)

    // Exchanges
    @Bean fun requestExchange() = TopicExchange(REQUEST_EXCHANGE)
    @Bean fun studentExchange() = TopicExchange(STUDENT_EXCHANGE)
    @Bean fun employeeExchange() = TopicExchange(EMPLOYEE_EXCHANGE)

    @Bean fun studentEventExchange() = TopicExchange(STUDENT_EVENT_EXCHANGE)
    @Bean fun employeeEventExchange() = TopicExchange(EMPLOYEE_EVENT_EXCHANGE)

    // Bindings
    @Bean
    fun requestBinding() = BindingBuilder
        .bind(requestQueue())
        .to(requestExchange())
        .with(REQUEST_ROUTE_KEY)

    @Bean
    fun studentBinding() = BindingBuilder
        .bind(studentQueue())
        .to(studentExchange())
        .with(STUDENT_ROUTE_KEY)

    @Bean
    fun employeeBinding() = BindingBuilder
        .bind(employeeQueue())
        .to(employeeExchange())
        .with(EMPLOYEE_ROUTE_KEY)
    
    @Bean
    fun studentPlanChangedBinding() = BindingBuilder
        .bind(studentPlanChangedQueue())
        .to(studentEventExchange())
        .with(STUDENT_PLAN_CHANGED_ROUTE_KEY)

    @Bean
    fun studentStatusChangedBinding() = BindingBuilder
        .bind(studentStatusChangedQueue())
        .to(studentEventExchange())
        .with(STUDENT_STATUS_CHANGED_ROUTE_KEY)

    @Bean
    fun studentDeletedBinding() = BindingBuilder
        .bind(studentDeletedQueue())
        .to(studentEventExchange())
        .with(STUDENT_DELETED_ROUTE_KEY)

    @Bean
    fun employeeRoleChangedBinding() = BindingBuilder
        .bind(employeeRoleChangedQueue())
        .to(employeeEventExchange())
        .with(EMPLOYEE_ROLE_CHANGED_ROUTE_KEY)

    @Bean
    fun employeeStatusChangedBinding() = BindingBuilder
        .bind(employeeStatusChangedQueue())
        .to(employeeEventExchange())
        .with(EMPLOYEE_STATUS_CHANGED_ROUTE_KEY)

    @Bean
    fun employeeDeletedBinding() = BindingBuilder
        .bind(employeeDeletedQueue())
        .to(employeeEventExchange())
        .with(EMPLOYEE_DELETED_ROUTE_KEY)
        
    // Jackson2JsonMessageConverter configurado para aceitar qualquer pacote
    @Bean
    fun messageConverter(): Jackson2JsonMessageConverter {
        val converter = Jackson2JsonMessageConverter()
        val typeMapper = DefaultJackson2JavaTypeMapper()
        typeMapper.setTrustedPackages("*") // Permite qualquer pacote
        converter.setJavaTypeMapper(typeMapper)
        return converter
    }

    // RabbitTemplate configurado para enviar mensagens JSON //
    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
        val template = RabbitTemplate(connectionFactory)
        template.messageConverter = Jackson2JsonMessageConverter()
        return template
    }
}