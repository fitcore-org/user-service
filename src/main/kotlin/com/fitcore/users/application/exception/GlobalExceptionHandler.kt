package com.fitcore.users.application.exception

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.beans.TypeMismatchException
import org.springframework.web.multipart.support.MissingServletRequestPartException
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    // 400 - Bad Request: IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(ex: Exception, request: WebRequest): ResponseEntity<Any> =
        buildErrorResponse(HttpStatus.BAD_REQUEST, ex.message ?: "Invalid request.")

    // 401 - Unauthorized
    @ExceptionHandler(BadCredentialsException::class, AuthenticationException::class)
    fun handleUnauthorized(ex: Exception, request: WebRequest): ResponseEntity<Any> =
        buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.message ?: "Authentication required.")

    // 403 - Forbidden
    @ExceptionHandler(AccessDeniedException::class)
    fun handleForbidden(ex: Exception, request: WebRequest): ResponseEntity<Any> =
        buildErrorResponse(HttpStatus.FORBIDDEN, ex.message ?: "Access denied.")

    // 404 - Not Found (NoSuchElementException)
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(ex: NoSuchElementException, request: WebRequest): ResponseEntity<Any> =
        buildErrorResponse(HttpStatus.NOT_FOUND, ex.message ?: "Resource not found.")

    // 409 - Conflict (Email)
    @ExceptionHandler(EmailAlreadyRegisteredException::class)
    fun handleEmailConflict(ex: EmailAlreadyRegisteredException, request: WebRequest): ResponseEntity<Any> =
        buildErrorResponse(HttpStatus.CONFLICT, ex.message ?: "E-mail already exists.")

    // 409 - Conflict (CPF)
    @ExceptionHandler(CpfAlreadyRegisteredException::class)
    fun handleCpfConflict(ex: CpfAlreadyRegisteredException, request: WebRequest): ResponseEntity<Any> =
        buildErrorResponse(HttpStatus.CONFLICT, ex.message ?: "CPF already exists.")

    // 422 - Unprocessable Entity: Bean validation error
    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errors = ex.bindingResult.fieldErrors
            .joinToString("; ") { "${it.field}: ${it.defaultMessage}" }
        return buildErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, errors)
    }

    // 400 - Bad Request: missing required parameter
    override fun handleMissingServletRequestParameter(
        ex: MissingServletRequestParameterException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> =
        buildErrorResponse(HttpStatus.BAD_REQUEST, ex.message ?: "Missing request parameter.")

    // 400 - Bad Request: missing multipart part
    override fun handleMissingServletRequestPart(
        ex: MissingServletRequestPartException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> =
        buildErrorResponse(HttpStatus.BAD_REQUEST, ex.message ?: "Missing request part.")

    // 400 - Bad Request: servlet binding error
    override fun handleServletRequestBindingException(
        ex: ServletRequestBindingException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> =
        buildErrorResponse(HttpStatus.BAD_REQUEST, ex.message ?: "Servlet request binding failed.")

    // 400 - Bad Request: type mismatch
    override fun handleTypeMismatch(
        ex: TypeMismatchException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> =
        buildErrorResponse(HttpStatus.BAD_REQUEST, ex.message ?: "Type mismatch.")

    // 405 - Method Not Allowed
    override fun handleHttpRequestMethodNotSupported(
        ex: HttpRequestMethodNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> =
        buildErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed.")

    // 415 - Unsupported Media Type
    override fun handleHttpMediaTypeNotSupported(
        ex: HttpMediaTypeNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> =
        buildErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Unsupported media type.")

    // 406 - Not Acceptable
    override fun handleHttpMediaTypeNotAcceptable(
        ex: HttpMediaTypeNotAcceptableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> =
        buildErrorResponse(HttpStatus.NOT_ACCEPTABLE, "Not acceptable.")

    // 500 - Internal Server Error (catch-all)
    @ExceptionHandler(Exception::class)
    fun handleAll(ex: Exception, request: WebRequest): ResponseEntity<Any> =
        buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.message ?: "Unexpected error occurred.")

    private fun buildErrorResponse(status: HttpStatus, message: String): ResponseEntity<Any> {
        val isDev = System.getenv("SPRING_PROFILES_ACTIVE") == "dev"
        val errorBody = mutableMapOf<String, Any>(
            "error" to status.reasonPhrase,
            "status" to status.value(),
            "message" to message
        )

        if (isDev) {
            errorBody["debug"] = Thread.currentThread().stackTrace
                .drop(2)
                .take(5)
                .map { "${it.className}.${it.methodName}:${it.lineNumber}" }
        }
        return ResponseEntity.status(status).body(errorBody)
    }
}