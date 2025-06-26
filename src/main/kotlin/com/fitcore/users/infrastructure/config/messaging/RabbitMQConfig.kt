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
        const val RESPONSE_SUCESSO_EXCHANGE = "cadastro-usuario-response-sucesso-exchange"
        const val RESPONSE_ERRO_EXCHANGE = "cadastro-usuario-response-erro-exchange"

        // Queues
        const val REQUEST_QUEUE = "cadastro-usuario-request-queue"
        const val RESPONSE_SUCESSO_QUEUE = "cadastro-usuario-response-sucesso-queue"
        const val RESPONSE_ERRO_QUEUE = "cadastro-usuario-response-erro-queue"

        // Routing Keys
        const val REQUEST_ROUTE_KEY = "cadastro-usuario-request-route-key"
        const val RESPONSE_SUCESSO_ROUTE_KEY = "cadastro-usuario-response-sucesso-route-key"
        const val RESPONSE_ERRO_ROUTE_KEY = "cadastro-usuario-response-erro-route-key"
    }

    // Queues
    @Bean fun requestQueue() = Queue(REQUEST_QUEUE, true)
    @Bean fun responseSucessoQueue() = Queue(RESPONSE_SUCESSO_QUEUE, true)
    @Bean fun responseErroQueue() = Queue(RESPONSE_ERRO_QUEUE, true)

    // Exchanges
    @Bean fun requestExchange() = TopicExchange(REQUEST_EXCHANGE)
    @Bean fun responseSucessoExchange() = TopicExchange(RESPONSE_SUCESSO_EXCHANGE)
    @Bean fun responseErroExchange() = TopicExchange(RESPONSE_ERRO_EXCHANGE)

    // Bindings
    @Bean
    fun requestBinding() = BindingBuilder
        .bind(requestQueue())
        .to(requestExchange())
        .with(REQUEST_ROUTE_KEY)

    @Bean
    fun responseSucessoBinding() = BindingBuilder
        .bind(responseSucessoQueue())
        .to(responseSucessoExchange())
        .with(RESPONSE_SUCESSO_ROUTE_KEY)

    @Bean
    fun responseErroBinding() = BindingBuilder
        .bind(responseErroQueue())
        .to(responseErroExchange())
        .with(RESPONSE_ERRO_ROUTE_KEY)
    
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