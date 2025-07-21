package com.fitcore.users.infrastructure.config.swagger.schema

import io.swagger.v3.oas.annotations.media.Schema

/**
 * Schemas customizados para melhorar a documentação dos DTOs de funcionários no Swagger.
 */
object EmployeeSchemas {

    @Schema(
        name = "EmployeeRequest",
        description = "Dados necessários para criação de um novo funcionário"
    )
    data class EmployeeRequestSchema(
        @Schema(
            description = "Nome completo do funcionário",
            example = "Maria Oliveira Santos",
            required = true
        )
        val name: String,

        @Schema(
            description = "Email do funcionário",
            example = "maria.oliveira@fitcore.com",
            required = true
        )
        val email: String,

        @Schema(
            description = "CPF do funcionário no formato XXX.XXX.XXX-XX",
            example = "987.654.321-00",
            required = true
        )
        val cpf: String,

        @Schema(
            description = "Data de nascimento do funcionário",
            example = "1988-07-22",
            required = true
        )
        val birthDate: String,

        @Schema(
            description = "Telefone do funcionário",
            example = "(11) 88888-8888",
            required = true
        )
        val phone: String,

        @Schema(
            description = "Cargo do funcionário",
            example = "INSTRUCTOR",
            allowableValues = ["ADMIN", "MANAGER", "INSTRUCTOR", "RECEPTIONIST"],
            required = true
        )
        val roleType: String,

        @Schema(
            description = "Data de contratação (opcional, padrão: data atual)",
            example = "2024-01-15"
        )
        val hireDate: String?
    )

    @Schema(
        name = "EmployeeResponse",
        description = "Dados de resposta de um funcionário"
    )
    data class EmployeeResponseSchema(
        @Schema(
            description = "ID único do funcionário",
            example = "456e7890-e89b-12d3-a456-426614174000"
        )
        val id: String,

        @Schema(
            description = "Nome completo do funcionário",
            example = "Maria Oliveira Santos"
        )
        val name: String,

        @Schema(
            description = "Email do funcionário",
            example = "maria.oliveira@fitcore.com"
        )
        val email: String,

        @Schema(
            description = "CPF do funcionário",
            example = "987.654.321-00"
        )
        val cpf: String,

        @Schema(
            description = "Data de nascimento do funcionário",
            example = "1988-07-22"
        )
        val birthDate: String,

        @Schema(
            description = "Telefone do funcionário",
            example = "(11) 88888-8888"
        )
        val phone: String,

        @Schema(
            description = "Cargo do funcionário",
            example = "INSTRUCTOR"
        )
        val role: String,

        @Schema(
            description = "Descrição detalhada do cargo",
            example = "Instrutor com acesso apenas às suas turmas e alunos"
        )
        val roleDescription: String,

        @Schema(
            description = "Status de atividade do funcionário",
            example = "true"
        )
        val active: Boolean,

        @Schema(
            description = "Data de contratação",
            example = "2024-01-15"
        )
        val hireDate: String,

        @Schema(
            description = "Data de desligamento (se aplicável)",
            example = "2024-12-31"
        )
        val terminationDate: String?,

        @Schema(
            description = "Data de registro no sistema",
            example = "2024-01-15T08:00:00"
        )
        val registrationDate: String,

        @Schema(
            description = "URL temporária da foto de perfil",
            example = "https://storage.fitcore.com/profiles/employee-456.jpg"
        )
        val profileUrl: String?
    )

    @Schema(
        name = "EmployeeUpdate",
        description = "Dados para atualização de um funcionário"
    )
    data class EmployeeUpdateSchema(
        @Schema(
            description = "Novo nome do funcionário",
            example = "Maria Oliveira Silva",
            required = true
        )
        val name: String,

        @Schema(
            description = "Novo email do funcionário",
            example = "maria.silva@fitcore.com",
            required = true
        )
        val email: String,

        @Schema(
            description = "Novo telefone do funcionário",
            example = "(11) 77777-7777",
            required = true
        )
        val phone: String,

        @Schema(
            description = "Novo cargo do funcionário",
            example = "MANAGER",
            allowableValues = ["ADMIN", "MANAGER", "INSTRUCTOR", "RECEPTIONIST"],
            required = true
        )
        val roleType: String
    )

    @Schema(
        name = "ChangeRole",
        description = "Dados para alteração do cargo do funcionário"
    )
    data class ChangeRoleSchema(
        @Schema(
            description = "Novo cargo do funcionário",
            example = "MANAGER",
            allowableValues = ["ADMIN", "MANAGER", "INSTRUCTOR", "RECEPTIONIST"],
            required = true
        )
        val roleType: String
    )

    @Schema(
        name = "EmployeeTermination",
        description = "Dados para desligamento de funcionário"
    )
    data class EmployeeTerminationSchema(
        @Schema(
            description = "Data de desligamento do funcionário",
            example = "2024-12-31",
            required = true
        )
        val terminationDate: String
    )
}
