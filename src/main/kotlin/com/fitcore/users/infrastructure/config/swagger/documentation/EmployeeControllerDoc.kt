package com.fitcore.users.infrastructure.config.swagger.documentation

import com.fitcore.users.infrastructure.config.swagger.SwaggerConstants
import com.fitcore.users.infrastructure.web.dto.employee.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile

/**
 * Interface de documentação para operações relacionadas aos funcionários.
 * Esta interface contém apenas as anotações do Swagger, mantendo o controller limpo.
 */
@Tag(name = SwaggerConstants.EMPLOYEE_TAG, description = "Operações para gerenciamento de funcionários do sistema")
interface EmployeeControllerDoc {

    @Operation(
        summary = "Criar novo funcionário",
        description = "Registra um novo funcionário no sistema com todos os dados necessários"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "201",
            description = SwaggerConstants.STATUS_201_DESCRIPTION,
            content = [Content(schema = Schema(implementation = EmployeeResponseDto::class))]
        ),
        ApiResponse(
            responseCode = "400",
            description = SwaggerConstants.STATUS_400_DESCRIPTION,
            content = [Content()]
        ),
        ApiResponse(
            responseCode = "409",
            description = "Email ou CPF já cadastrado",
            content = [Content()]
        )
    ])
    fun createEmployee(
        @RequestBody(
            description = "Dados do funcionário a ser criado",
            required = true,
            content = [Content(schema = Schema(implementation = EmployeeRequestDto::class))]
        )
        request: EmployeeRequestDto
    ): ResponseEntity<EmployeeResponseDto>

    @Operation(
        summary = "Upload de foto de perfil",
        description = "Faz upload da foto de perfil de um funcionário existente"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = SwaggerConstants.STATUS_200_DESCRIPTION,
            content = [Content(schema = Schema(implementation = EmployeeResponseDto::class))]
        ),
        ApiResponse(
            responseCode = "404",
            description = SwaggerConstants.STATUS_404_DESCRIPTION,
            content = [Content()]
        )
    ])
    fun uploadProfile(
        @Parameter(description = SwaggerConstants.ID_PARAM_DESCRIPTION)
        id: String,
        @Parameter(description = SwaggerConstants.FILE_PARAM_DESCRIPTION)
        file: MultipartFile
    ): ResponseEntity<EmployeeResponseDto>

    @Operation(
        summary = "Atualizar foto de perfil",
        description = "Atualiza a foto de perfil existente de um funcionário"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = SwaggerConstants.STATUS_200_DESCRIPTION,
            content = [Content(schema = Schema(implementation = EmployeeResponseDto::class))]
        ),
        ApiResponse(
            responseCode = "404",
            description = SwaggerConstants.STATUS_404_DESCRIPTION,
            content = [Content()]
        )
    ])
    fun updateProfile(
        @Parameter(description = SwaggerConstants.ID_PARAM_DESCRIPTION)
        id: String,
        @Parameter(description = SwaggerConstants.FILE_PARAM_DESCRIPTION)
        file: MultipartFile
    ): ResponseEntity<EmployeeResponseDto>

    @Operation(
        summary = "Remover foto de perfil",
        description = "Remove a foto de perfil de um funcionário"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = SwaggerConstants.STATUS_200_DESCRIPTION,
            content = [Content(schema = Schema(implementation = EmployeeResponseDto::class))]
        ),
        ApiResponse(
            responseCode = "204",
            description = "Funcionário não possui foto de perfil",
            content = [Content()]
        ),
        ApiResponse(
            responseCode = "404",
            description = SwaggerConstants.STATUS_404_DESCRIPTION,
            content = [Content()]
        )
    ])
    fun deleteProfile(
        @Parameter(description = SwaggerConstants.ID_PARAM_DESCRIPTION)
        id: String
    ): ResponseEntity<EmployeeResponseDto>

    @Operation(
        summary = "Obter URL da foto de perfil",
        description = "Retorna uma URL temporária para acesso à foto de perfil do funcionário"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = SwaggerConstants.STATUS_200_DESCRIPTION,
            content = [Content(schema = Schema(implementation = Map::class))]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Funcionário ou foto não encontrada",
            content = [Content()]
        )
    ])
    fun getProfileUrl(
        @Parameter(description = SwaggerConstants.ID_PARAM_DESCRIPTION)
        id: String
    ): ResponseEntity<Map<String, String>>

    @Operation(
        summary = "Listar todos os funcionários",
        description = "Retorna uma lista com todos os funcionários cadastrados no sistema"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = SwaggerConstants.STATUS_200_DESCRIPTION,
            content = [Content(schema = Schema(implementation = Array<EmployeeResponseDto>::class))]
        )
    ])
    fun getAllEmployees(): ResponseEntity<List<EmployeeResponseDto>>

    @Operation(
        summary = "Buscar funcionário por ID",
        description = "Retorna os dados de um funcionário específico pelo seu ID"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = SwaggerConstants.STATUS_200_DESCRIPTION,
            content = [Content(schema = Schema(implementation = EmployeeResponseDto::class))]
        ),
        ApiResponse(
            responseCode = "404",
            description = SwaggerConstants.STATUS_404_DESCRIPTION,
            content = [Content()]
        )
    ])
    fun getEmployeeById(
        @Parameter(description = SwaggerConstants.ID_PARAM_DESCRIPTION)
        id: String
    ): ResponseEntity<EmployeeResponseDto>

    @Operation(
        summary = "Buscar funcionário por email",
        description = "Retorna os dados de um funcionário específico pelo seu email"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = SwaggerConstants.STATUS_200_DESCRIPTION,
            content = [Content(schema = Schema(implementation = EmployeeResponseDto::class))]
        ),
        ApiResponse(
            responseCode = "404",
            description = SwaggerConstants.STATUS_404_DESCRIPTION,
            content = [Content()]
        )
    ])
    fun getEmployeeByEmail(
        @Parameter(description = SwaggerConstants.EMAIL_PARAM_DESCRIPTION)
        email: String
    ): ResponseEntity<EmployeeResponseDto>

    @Operation(
        summary = "Buscar funcionário por CPF",
        description = "Retorna os dados de um funcionário específico pelo seu CPF"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = SwaggerConstants.STATUS_200_DESCRIPTION,
            content = [Content(schema = Schema(implementation = EmployeeResponseDto::class))]
        ),
        ApiResponse(
            responseCode = "404",
            description = SwaggerConstants.STATUS_404_DESCRIPTION,
            content = [Content()]
        )
    ])
    fun getEmployeeByCpf(
        @Parameter(description = SwaggerConstants.CPF_PARAM_DESCRIPTION)
        cpf: String
    ): ResponseEntity<EmployeeResponseDto>

    @Operation(
        summary = "Buscar funcionários por cargo",
        description = "Retorna uma lista de funcionários que possuem um cargo específico"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = SwaggerConstants.STATUS_200_DESCRIPTION,
            content = [Content(schema = Schema(implementation = Array<EmployeeResponseDto>::class))]
        )
    ])
    fun getEmployeesByRole(
        @Parameter(description = SwaggerConstants.ROLE_PARAM_DESCRIPTION)
        role: String
    ): ResponseEntity<List<EmployeeResponseDto>>

    @Operation(
        summary = "Listar funcionários ativos",
        description = "Retorna uma lista com todos os funcionários ativos no sistema"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = SwaggerConstants.STATUS_200_DESCRIPTION,
            content = [Content(schema = Schema(implementation = Array<EmployeeResponseDto>::class))]
        )
    ])
    fun getActiveEmployees(): ResponseEntity<List<EmployeeResponseDto>>

    @Operation(
        summary = "Atualizar dados do funcionário",
        description = "Atualiza os dados pessoais e informações do cargo de um funcionário"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = SwaggerConstants.STATUS_200_DESCRIPTION,
            content = [Content(schema = Schema(implementation = EmployeeResponseDto::class))]
        ),
        ApiResponse(
            responseCode = "400",
            description = SwaggerConstants.STATUS_400_DESCRIPTION,
            content = [Content()]
        ),
        ApiResponse(
            responseCode = "404",
            description = SwaggerConstants.STATUS_404_DESCRIPTION,
            content = [Content()]
        )
    ])
    fun updateEmployee(
        @Parameter(description = SwaggerConstants.ID_PARAM_DESCRIPTION)
        id: String,
        @RequestBody(
            description = "Dados atualizados do funcionário",
            required = true,
            content = [Content(schema = Schema(implementation = EmployeeUpdateDto::class))]
        )
        request: EmployeeUpdateDto
    ): ResponseEntity<EmployeeResponseDto>

    @Operation(
        summary = "Alterar cargo do funcionário",
        description = "Altera o cargo de um funcionário existente"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = SwaggerConstants.STATUS_200_DESCRIPTION,
            content = [Content(schema = Schema(implementation = EmployeeResponseDto::class))]
        ),
        ApiResponse(
            responseCode = "404",
            description = SwaggerConstants.STATUS_404_DESCRIPTION,
            content = [Content()]
        )
    ])
    fun changeRole(
        @Parameter(description = SwaggerConstants.ID_PARAM_DESCRIPTION)
        id: String,
        @RequestBody(
            description = "Novo cargo do funcionário",
            required = true,
            content = [Content(schema = Schema(implementation = ChangeRoleDto::class))]
        )
        request: ChangeRoleDto
    ): ResponseEntity<EmployeeResponseDto>

    @Operation(
        summary = "Reativar funcionário",
        description = "Reativa um funcionário que estava desligado"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = SwaggerConstants.STATUS_200_DESCRIPTION,
            content = [Content(schema = Schema(implementation = EmployeeResponseDto::class))]
        ),
        ApiResponse(
            responseCode = "404",
            description = SwaggerConstants.STATUS_404_DESCRIPTION,
            content = [Content()]
        )
    ])
    fun activateEmployee(
        @Parameter(description = SwaggerConstants.ID_PARAM_DESCRIPTION)
        id: String
    ): ResponseEntity<EmployeeResponseDto>

    @Operation(
        summary = "Desligar funcionário",
        description = "Desliga um funcionário do sistema com data de desligamento"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = SwaggerConstants.STATUS_200_DESCRIPTION,
            content = [Content(schema = Schema(implementation = EmployeeResponseDto::class))]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Data de desligamento inválida",
            content = [Content()]
        ),
        ApiResponse(
            responseCode = "404",
            description = SwaggerConstants.STATUS_404_DESCRIPTION,
            content = [Content()]
        )
    ])
    fun deactivateEmployee(
        @Parameter(description = SwaggerConstants.ID_PARAM_DESCRIPTION)
        id: String,
        @RequestBody(
            description = "Data de desligamento do funcionário",
            required = true,
            content = [Content(schema = Schema(implementation = EmployeeTerminationDto::class))]
        )
        request: EmployeeTerminationDto
    ): ResponseEntity<EmployeeResponseDto>

    @Operation(
        summary = "Excluir funcionário",
        description = "Remove permanentemente um funcionário do sistema"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "204",
            description = SwaggerConstants.STATUS_204_DESCRIPTION,
            content = [Content()]
        ),
        ApiResponse(
            responseCode = "404",
            description = SwaggerConstants.STATUS_404_DESCRIPTION,
            content = [Content()]
        )
    ])
    fun deleteEmployee(
        @Parameter(description = SwaggerConstants.ID_PARAM_DESCRIPTION)
        id: String
    ): ResponseEntity<Void>
}
