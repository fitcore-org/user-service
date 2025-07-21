package com.fitcore.users.infrastructure.config.swagger

import io.swagger.v3.oas.models.examples.Example
import io.swagger.v3.oas.models.media.Content
import io.swagger.v3.oas.models.media.MediaType
import org.springframework.context.annotation.Configuration

/**
 * Configurações avançadas e exemplos para documentação Swagger.
 * Esta classe contém configurações específicas para melhorar a experiência
 * de documentação da API.
 */
@Configuration
class SwaggerExamplesConfig {
    
    companion object {
        
        /**
         * Exemplos de requisições para estudantes
         */
        object StudentExamples {
            
            val CREATE_STUDENT_BASIC = Example().apply {
                summary = "Estudante com plano básico"
                description = "Exemplo de criação de estudante com plano básico"
                value = mapOf(
                    "name" to "Maria Santos",
                    "email" to "maria.santos@email.com",
                    "cpf" to "123.456.789-00",
                    "birthDate" to "1998-05-20",
                    "phone" to "(11) 99999-1111",
                    "planType" to "BASIC",
                    "weight" to 65.0,
                    "height" to 165
                )
            }
            
            val CREATE_STUDENT_PREMIUM = Example().apply {
                summary = "Estudante com plano premium"
                description = "Exemplo de criação de estudante com plano premium"
                value = mapOf(
                    "name" to "João Silva",
                    "email" to "joao.silva@email.com",
                    "cpf" to "987.654.321-00",
                    "birthDate" to "1995-03-15",
                    "phone" to "(11) 88888-2222",
                    "planType" to "PREMIUM",
                    "weight" to 80.5,
                    "height" to 180
                )
            }
            
            val UPDATE_PHYSICAL_DATA = Example().apply {
                summary = "Atualização de dados físicos"
                description = "Exemplo de atualização apenas do peso e altura"
                value = mapOf(
                    "weight" to 78.5,
                    "height" to 182
                )
            }
        }
        
        /**
         * Exemplos de requisições para funcionários
         */
        object EmployeeExamples {
            
            val CREATE_INSTRUCTOR = Example().apply {
                summary = "Instrutor"
                description = "Exemplo de criação de instrutor"
                value = mapOf(
                    "name" to "Carlos Oliveira",
                    "email" to "carlos.oliveira@fitcore.com",
                    "cpf" to "111.222.333-44",
                    "birthDate" to "1985-10-15",
                    "phone" to "(11) 77777-3333",
                    "roleType" to "INSTRUCTOR",
                    "hireDate" to "2024-01-15"
                )
            }
            
            val CREATE_MANAGER = Example().apply {
                summary = "Gerente"
                description = "Exemplo de criação de gerente"
                value = mapOf(
                    "name" to "Ana Costa",
                    "email" to "ana.costa@fitcore.com",
                    "cpf" to "555.666.777-88",
                    "birthDate" to "1982-07-22",
                    "phone" to "(11) 66666-4444",
                    "roleType" to "MANAGER",
                    "hireDate" to "2024-01-01"
                )
            }
            
            val TERMINATION_REQUEST = Example().apply {
                summary = "Desligamento"
                description = "Exemplo de desligamento de funcionário"
                value = mapOf(
                    "terminationDate" to "2024-12-31"
                )
            }
        }
        
        /**
         * Exemplos de respostas de erro
         */
        object ErrorExamples {
            
            val VALIDATION_ERROR = Example().apply {
                summary = "Erro de validação"
                description = "Erro quando dados de entrada são inválidos"
                value = mapOf(
                    "timestamp" to "2024-01-15T10:30:00Z",
                    "status" to 400,
                    "error" to "Bad Request",
                    "message" to "Email já cadastrado no sistema",
                    "path" to "/api/students"
                )
            }
            
            val NOT_FOUND_ERROR = Example().apply {
                summary = "Recurso não encontrado"
                description = "Erro quando recurso solicitado não existe"
                value = mapOf(
                    "timestamp" to "2024-01-15T10:30:00Z",
                    "status" to 404,
                    "error" to "Not Found",
                    "message" to "Estudante não encontrado",
                    "path" to "/api/students/123e4567-e89b-12d3-a456-426614174000"
                )
            }
            
            val CONFLICT_ERROR = Example().apply {
                summary = "Conflito de dados"
                description = "Erro quando há conflito com dados existentes"
                value = mapOf(
                    "timestamp" to "2024-01-15T10:30:00Z",
                    "status" to 409,
                    "error" to "Conflict",
                    "message" to "CPF já cadastrado no sistema",
                    "path" to "/api/employees"
                )
            }
        }
        
        /**
         * Exemplos de respostas de sucesso
         */
        object SuccessExamples {
            
            val STUDENT_RESPONSE = Example().apply {
                summary = "Estudante criado"
                description = "Resposta de sucesso ao criar estudante"
                value = mapOf(
                    "id" to "123e4567-e89b-12d3-a456-426614174000",
                    "name" to "João Silva",
                    "email" to "joao.silva@email.com",
                    "cpf" to "987.654.321-00",
                    "birthDate" to "1995-03-15",
                    "phone" to "(11) 88888-2222",
                    "planName" to "Premium",
                    "planDescription" to "Acesso completo + aulas em grupo",
                    "weight" to 80.5,
                    "height" to 180,
                    "active" to true,
                    "registrationDate" to "2024-01-15T10:30:00",
                    "profileUrl" to null
                )
            }
            
            val PROFILE_URL_RESPONSE = Example().apply {
                summary = "URL da foto de perfil"
                description = "Resposta com URL temporária para acesso à foto"
                value = mapOf(
                    "profileUrl" to "https://storage.fitcore.com/profiles/temp-url-123?expires=1642252800"
                )
            }
        }
    }
    
    /**
     * Configurações de conteúdo para diferentes tipos de media
     */
    fun getContentWithExamples(vararg examples: Example): Content {
        return Content().addMediaType(
            "application/json",
            MediaType().apply {
                examples.forEach { example ->
                    addExamples(example.summary, example)
                }
            }
        )
    }
}
