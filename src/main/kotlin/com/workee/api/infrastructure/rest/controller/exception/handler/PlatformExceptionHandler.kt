package com.workee.api.infrastructure.rest.controller.exception.handler

import com.workee.api.domain.exception.ErrorCode
import com.workee.api.domain.exception.WorkeeException
import com.workee.api.infrastructure.rest.controller.exception.message.ErrorResponse
import com.workee.api.infrastructure.rest.controller.exception.message.mapper.ExceptionRestMapper
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authorization.AuthorizationDeniedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class PlatformExceptionHandler(private val mapper: ExceptionRestMapper) {

    companion object {
        private const val LOG_HEADER = "[PLATFORM EXCEPTION HANDLER]"
        private val logger = LoggerFactory.getLogger(PlatformExceptionHandler::class.java)
    }

    @ExceptionHandler(WorkeeException::class)
    fun handle(ex: WorkeeException): ResponseEntity<ErrorResponse> {

        logger.warn("$LOG_HEADER WorkeeException: ${ex.message}")

        val body = mapper.asErrorResponse(ex)
        return ResponseEntity(body, ex.status)
    }

    @ExceptionHandler(AuthorizationDeniedException::class)
    fun handle(ex: AuthorizationDeniedException): ResponseEntity<ErrorResponse> {
        logger.warn("$LOG_HEADER AuthorizationDeniedException: ${ex.message}")
        val body = mapper.asErrorResponse(ErrorCode.ACCESS_DENIED, "Access Denied")
        return ResponseEntity(body, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handle(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        logger.warn("$LOG_HEADER MethodArgumentNotValidException: ${ex.message}")

        val errors = ex.bindingResult.fieldErrors.map { fieldError ->
            "${fieldError.field}: ${fieldError.defaultMessage}"
        }

        val body = mapper.asErrorResponse(ErrorCode.BAD_REQUEST, errors.joinToString(", "))
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handle(ex: MissingServletRequestParameterException): ResponseEntity<ErrorResponse> {
        logger.warn("$LOG_HEADER MissingServletRequestParameterException: ${ex.message}")
        val body = mapper.asErrorResponse(ErrorCode.BAD_REQUEST, "Bad Request")
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handle(): ResponseEntity<ErrorResponse> {
        logger.error("$LOG_HEADER GenericException")
        val body = mapper.asErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Server Error")
        return ResponseEntity(body, HttpStatus.INTERNAL_SERVER_ERROR)
    }

}