package com.fitcore.users.infrastructure.config.swagger.documentation

import com.fitcore.users.infrastructure.config.swagger.SwaggerConstants
import com.fitcore.users.infrastructure.web.dto.student.*
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
 * Interface de documentação para operações relacionadas aos estudantes.
 * Esta interface contém apenas as anotações do Swagger, mantendo o controller limpo.
 */
@Tag(name = SwaggerConstants.STUDENT_TAG, description = "Operações para gerenciamento de estudantes do sistema")
interface StudentControllerDoc {

    @Operation(
        summary = "Criar novo estudante",
        description = "Registra um novo estudante no sistema com todos os dados necessários"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "201",
            description = SwaggerConstants.STATUS_201_DESCRIPTION,
            content = [Content(schema = Schema(implementation = StudentResponseDto::class))]
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
    fun createStudent(
        @RequestBody(
            description = "Dados do estudante a ser criado",
            required = true,
            content = [Content(schema = Schema(implementation = StudentRequestDto::class))]
        )
        request: StudentRequestDto
    ): ResponseEntity<StudentResponseDto>

    @Operation(
        summary = "Upload de foto de perfil",
        description = "Faz upload da foto de perfil de um estudante existente"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = SwaggerConstants.STATUS_200_DESCRIPTION,
            content = [Content(schema = Schema(implementation = StudentResponseDto::class))]
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
    ): ResponseEntity<StudentResponseDto>

    @Operation(
        summary = "Atualizar foto de perfil",
        description = "Atualiza a foto de perfil existente de um estudante"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = SwaggerConstants.STATUS_200_DESCRIPTION,
            content = [Content(schema = Schema(implementation = StudentResponseDto::class))]
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
    ): ResponseEntity<StudentResponseDto>

    @Operation(
        summary = "Remover foto de perfil",
        description = "Remove a foto de perfil de um estudante"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = SwaggerConstants.STATUS_200_DESCRIPTION,
            content = [Content(schema = Schema(implementation = StudentResponseDto::class))]
        ),
        ApiResponse(
            responseCode = "204",
            description = "Estudante não possui foto de perfil",
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
    ): ResponseEntity<StudentResponseDto>

    @Operation(
        summary = "Obter URL da foto de perfil",
        description = "Retorna uma URL temporária para acesso à foto de perfil do estudante"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = SwaggerConstants.STATUS_200_DESCRIPTION,
            content = [Content(schema = Schema(implementation = Map::class))]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Estudante ou foto não encontrada",
            content = [Content()]
        )
    ])
    fun getProfileUrl(
        @Parameter(description = SwaggerConstants.ID_PARAM_DESCRIPTION)
        id: String
    ): ResponseEntity<Map<String, String>>

    @Operation(
        summary = "Listar todos os estudantes",
        description = "Retorna uma lista com todos os estudantes cadastrados no sistema"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = SwaggerConstants.STATUS_200_DESCRIPTION,
            content = [Content(schema = Schema(implementation = Array<StudentResponseDto>::class))]
        )
    ])
    fun getAllStudents(): ResponseEntity<List<StudentResponseDto>>

    @Operation(
        summary = "Buscar estudante por ID",
        description = "Retorna os dados de um estudante específico pelo seu ID"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = SwaggerConstants.STATUS_200_DESCRIPTION,
            content = [Content(schema = Schema(implementation = StudentResponseDto::class))]
        ),
        ApiResponse(
            responseCode = "404",
            description = SwaggerConstants.STATUS_404_DESCRIPTION,
            content = [Content()]
        )
    ])
    fun getStudentById(
        @Parameter(description = SwaggerConstants.ID_PARAM_DESCRIPTION)
        id: String
    ): ResponseEntity<StudentResponseDto>

    @Operation(
        summary = "Buscar estudante por email",
        description = "Retorna os dados de um estudante específico pelo seu email"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = SwaggerConstants.STATUS_200_DESCRIPTION,
            content = [Content(schema = Schema(implementation = StudentResponseDto::class))]
        ),
        ApiResponse(
            responseCode = "404",
            description = SwaggerConstants.STATUS_404_DESCRIPTION,
            content = [Content()]
        )
    ])
    fun getStudentByEmail(
        @Parameter(description = SwaggerConstants.EMAIL_PARAM_DESCRIPTION)
        email: String
    ): ResponseEntity<StudentResponseDto>

    @Operation(
        summary = "Buscar estudante por CPF",
        description = "Retorna os dados de um estudante específico pelo seu CPF"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = SwaggerConstants.STATUS_200_DESCRIPTION,
            content = [Content(schema = Schema(implementation = StudentResponseDto::class))]
        ),
        ApiResponse(
            responseCode = "404",
            description = SwaggerConstants.STATUS_404_DESCRIPTION,
            content = [Content()]
        )
    ])
    fun getStudentByCpf(
        @Parameter(description = SwaggerConstants.CPF_PARAM_DESCRIPTION)
        cpf: String
    ): ResponseEntity<StudentResponseDto>

    @Operation(
        summary = "Buscar estudantes por plano",
        description = "Retorna uma lista de estudantes que possuem um plano específico"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = SwaggerConstants.STATUS_200_DESCRIPTION,
            content = [Content(schema = Schema(implementation = Array<StudentResponseDto>::class))]
        )
    ])
    fun getStudentsByPlan(
        @Parameter(description = SwaggerConstants.PLAN_PARAM_DESCRIPTION)
        planType: String
    ): ResponseEntity<List<StudentResponseDto>>

    @Operation(
        summary = "Listar estudantes ativos",
        description = "Retorna uma lista com todos os estudantes ativos no sistema"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = SwaggerConstants.STATUS_200_DESCRIPTION,
            content = [Content(schema = Schema(implementation = Array<StudentResponseDto>::class))]
        )
    ])
    fun getActiveStudents(): ResponseEntity<List<StudentResponseDto>>

    @Operation(
        summary = "Atualizar dados do estudante",
        description = "Atualiza os dados pessoais e informações do plano de um estudante"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = SwaggerConstants.STATUS_200_DESCRIPTION,
            content = [Content(schema = Schema(implementation = StudentResponseDto::class))]
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
    fun updateStudent(
        @Parameter(description = SwaggerConstants.ID_PARAM_DESCRIPTION)
        id: String,
        @RequestBody(
            description = "Dados atualizados do estudante",
            required = true,
            content = [Content(schema = Schema(implementation = StudentUpdateDto::class))]
        )
        request: StudentUpdateDto
    ): ResponseEntity<StudentResponseDto>

    @Operation(
        summary = "Atualizar dados físicos",
        description = "Atualiza apenas o peso e altura de um estudante"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = SwaggerConstants.STATUS_200_DESCRIPTION,
            content = [Content(schema = Schema(implementation = StudentResponseDto::class))]
        ),
        ApiResponse(
            responseCode = "404",
            description = SwaggerConstants.STATUS_404_DESCRIPTION,
            content = [Content()]
        )
    ])
    fun updatePhysicalData(
        @Parameter(description = SwaggerConstants.ID_PARAM_DESCRIPTION)
        id: String,
        @RequestBody(
            description = "Novos dados físicos do estudante",
            required = true,
            content = [Content(schema = Schema(implementation = PhysicalDataUpdateDto::class))]
        )
        request: PhysicalDataUpdateDto
    ): ResponseEntity<StudentResponseDto>

    @Operation(
        summary = "Alterar plano do estudante",
        description = "Altera o plano de um estudante existente"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = SwaggerConstants.STATUS_200_DESCRIPTION,
            content = [Content(schema = Schema(implementation = StudentResponseDto::class))]
        ),
        ApiResponse(
            responseCode = "404",
            description = SwaggerConstants.STATUS_404_DESCRIPTION,
            content = [Content()]
        )
    ])
    fun changePlan(
        @Parameter(description = SwaggerConstants.ID_PARAM_DESCRIPTION)
        id: String,
        @RequestBody(
            description = "Novo plano do estudante",
            required = true,
            content = [Content(schema = Schema(implementation = ChangePlanDto::class))]
        )
        request: ChangePlanDto
    ): ResponseEntity<StudentResponseDto>

    @Operation(
        summary = "Ativar estudante",
        description = "Ativa um estudante que estava inativo no sistema"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = SwaggerConstants.STATUS_200_DESCRIPTION,
            content = [Content(schema = Schema(implementation = StudentResponseDto::class))]
        ),
        ApiResponse(
            responseCode = "404",
            description = SwaggerConstants.STATUS_404_DESCRIPTION,
            content = [Content()]
        )
    ])
    fun activateStudent(
        @Parameter(description = SwaggerConstants.ID_PARAM_DESCRIPTION)
        id: String
    ): ResponseEntity<StudentResponseDto>

    @Operation(
        summary = "Desativar estudante",
        description = "Desativa um estudante no sistema sem removê-lo permanentemente"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = SwaggerConstants.STATUS_200_DESCRIPTION,
            content = [Content(schema = Schema(implementation = StudentResponseDto::class))]
        ),
        ApiResponse(
            responseCode = "404",
            description = SwaggerConstants.STATUS_404_DESCRIPTION,
            content = [Content()]
        )
    ])
    fun deactivateStudent(
        @Parameter(description = SwaggerConstants.ID_PARAM_DESCRIPTION)
        id: String
    ): ResponseEntity<StudentResponseDto>

    @Operation(
        summary = "Excluir estudante",
        description = "Remove permanentemente um estudante do sistema"
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
    fun deleteStudent(
        @Parameter(description = SwaggerConstants.ID_PARAM_DESCRIPTION)
        id: String
    ): ResponseEntity<Void>
}
