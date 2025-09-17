package com.workee.api.infrastructure.rest.exception.handler

import com.workee.api.domain.exception.ErrorCode
import com.workee.api.domain.exception.WorkeeException
import com.workee.api.infrastructure.rest.exception.message.ErrorResponse
import com.workee.api.infrastructure.rest.exception.message.mapper.IExceptionRestMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authorization.AuthorizationDeniedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class PlatformExceptionHandler(private val mapper: IExceptionRestMapper) {

    @ExceptionHandler(WorkeeException::class)
    fun handleWorkeeException(ex: WorkeeException): ResponseEntity<ErrorResponse> {
        val body = mapper.asErrorResponse(ex)
        val status = getHttpStatusCode(ex.code)
        return ResponseEntity(body, status)
    }

    @ExceptionHandler(AuthorizationDeniedException::class)
    fun handleAccessDenied(ex: AuthorizationDeniedException): ResponseEntity<ErrorResponse> {
        val body = mapper.asErrorResponse(ErrorCode.ACCESS_DENIED, "Access Denied")
        return ResponseEntity(body, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(): ResponseEntity<ErrorResponse> {
        val body = mapper.asErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Server Error")
        return ResponseEntity(body, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    fun getHttpStatusCode(errorCode: ErrorCode): HttpStatus {
        return when (errorCode) {
            ErrorCode.INVALID_TOKEN -> HttpStatus.UNAUTHORIZED
            else -> {
                HttpStatus.INTERNAL_SERVER_ERROR
            }
        }
    }
}