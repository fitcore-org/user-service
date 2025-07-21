package com.fitcore.users.infrastructure.config.swagger.schema

import io.swagger.v3.oas.annotations.media.Schema

/**
 * Schemas customizados para melhorar a documentação dos DTOs no Swagger.
 * Estes schemas fornecem exemplos e descrições detalhadas dos campos.
 */
object StudentSchemas {

    @Schema(
        name = "StudentRequest",
        description = "Dados necessários para criação de um novo estudante"
    )
    data class StudentRequestSchema(
        @Schema(
            description = "Nome completo do estudante",
            example = "João Silva Santos",
            required = true
        )
        val name: String,

        @Schema(
            description = "Email do estudante",
            example = "joao.silva@email.com",
            required = true
        )
        val email: String,

        @Schema(
            description = "CPF do estudante no formato XXX.XXX.XXX-XX",
            example = "123.456.789-00",
            required = true
        )
        val cpf: String,

        @Schema(
            description = "Data de nascimento do estudante",
            example = "1995-03-15",
            required = true
        )
        val birthDate: String,

        @Schema(
            description = "Telefone do estudante",
            example = "(11) 99999-9999",
            required = true
        )
        val phone: String,

        @Schema(
            description = "Tipo do plano do estudante",
            example = "PREMIUM",
            allowableValues = ["BASIC", "PREMIUM", "VIP"],
            required = true
        )
        val planType: String,

        @Schema(
            description = "Peso do estudante em quilogramas",
            example = "75.5"
        )
        val weight: Double?,

        @Schema(
            description = "Altura do estudante em centímetros",
            example = "175"
        )
        val height: Int?
    )

    @Schema(
        name = "StudentResponse",
        description = "Dados de resposta de um estudante"
    )
    data class StudentResponseSchema(
        @Schema(
            description = "ID único do estudante",
            example = "123e4567-e89b-12d3-a456-426614174000"
        )
        val id: String,

        @Schema(
            description = "Nome completo do estudante",
            example = "João Silva Santos"
        )
        val name: String,

        @Schema(
            description = "Email do estudante",
            example = "joao.silva@email.com"
        )
        val email: String,

        @Schema(
            description = "CPF do estudante",
            example = "123.456.789-00"
        )
        val cpf: String,

        @Schema(
            description = "Data de nascimento do estudante",
            example = "1995-03-15"
        )
        val birthDate: String,

        @Schema(
            description = "Telefone do estudante",
            example = "(11) 99999-9999"
        )
        val phone: String,

        @Schema(
            description = "Nome do plano do estudante",
            example = "Premium"
        )
        val planName: String,

        @Schema(
            description = "Descrição do plano",
            example = "Acesso completo a todas as áreas"
        )
        val planDescription: String,

        @Schema(
            description = "Peso atual do estudante em kg",
            example = "75.5"
        )
        val weight: Double?,

        @Schema(
            description = "Altura do estudante em cm",
            example = "175"
        )
        val height: Int?,

        @Schema(
            description = "Status de atividade do estudante",
            example = "true"
        )
        val active: Boolean,

        @Schema(
            description = "Data de registro no sistema",
            example = "2024-01-15T10:30:00"
        )
        val registrationDate: String,

        @Schema(
            description = "URL temporária da foto de perfil",
            example = "https://storage.fitcore.com/profiles/student-123.jpg"
        )
        val profileUrl: String?
    )

    @Schema(
        name = "PhysicalDataUpdate",
        description = "Dados para atualização das informações físicas do estudante"
    )
    data class PhysicalDataUpdateSchema(
        @Schema(
            description = "Novo peso do estudante em quilogramas",
            example = "78.0"
        )
        val weight: Double?,

        @Schema(
            description = "Nova altura do estudante em centímetros",
            example = "180"
        )
        val height: Int?
    )

    @Schema(
        name = "ChangePlan",
        description = "Dados para alteração do plano do estudante"
    )
    data class ChangePlanSchema(
        @Schema(
            description = "Novo tipo de plano",
            example = "VIP",
            allowableValues = ["BASIC", "PREMIUM"],
            required = true
        )
        val planType: String
    )
}
