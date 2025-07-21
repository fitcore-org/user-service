package com.fitcore.users.infrastructure.config.swagger.annotation

import com.fitcore.users.infrastructure.config.swagger.SwaggerConstants
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses

/**
 * Anotações customizadas para simplificar a documentação Swagger.
 * Estas anotações encapsulam combinações comuns de respostas HTTP.
 */

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@ApiResponses(value = [
    ApiResponse(
        responseCode = "200",
        description = SwaggerConstants.STATUS_200_DESCRIPTION
    ),
    ApiResponse(
        responseCode = "404",
        description = SwaggerConstants.STATUS_404_DESCRIPTION,
        content = [Content()]
    )
])
annotation class StandardGetResponse

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@ApiResponses(value = [
    ApiResponse(
        responseCode = "201",
        description = SwaggerConstants.STATUS_201_DESCRIPTION
    ),
    ApiResponse(
        responseCode = "400",
        description = SwaggerConstants.STATUS_400_DESCRIPTION,
        content = [Content()]
    ),
    ApiResponse(
        responseCode = "409",
        description = "Recurso já existe (email ou CPF duplicado)",
        content = [Content()]
    )
])
annotation class StandardCreateResponse

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@ApiResponses(value = [
    ApiResponse(
        responseCode = "200",
        description = SwaggerConstants.STATUS_200_DESCRIPTION
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
annotation class StandardUpdateResponse

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
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
annotation class StandardDeleteResponse

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@ApiResponses(value = [
    ApiResponse(
        responseCode = "200",
        description = SwaggerConstants.STATUS_200_DESCRIPTION
    ),
    ApiResponse(
        responseCode = "204",
        description = "Usuário não possui foto de perfil",
        content = [Content()]
    ),
    ApiResponse(
        responseCode = "404",
        description = SwaggerConstants.STATUS_404_DESCRIPTION,
        content = [Content()]
    )
])
annotation class ProfileManagementResponse
