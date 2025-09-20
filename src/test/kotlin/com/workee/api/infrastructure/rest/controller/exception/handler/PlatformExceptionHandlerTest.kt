package com.workee.api.infrastructure.rest.controller.exception.handler

import com.workee.api.domain.exception.*
import com.workee.api.infrastructure.rest.controller.exception.message.ErrorResponse
import com.workee.api.infrastructure.rest.controller.exception.message.mapper.ExceptionRestMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import org.mockito.kotlin.verify
import org.springframework.http.HttpStatus
import org.springframework.security.authorization.AuthorizationDeniedException
import org.springframework.web.bind.MissingServletRequestParameterException

@ExtendWith(MockitoExtension::class)
class PlatformExceptionHandlerTest {

    @Mock
    private lateinit var mapper: ExceptionRestMapper

    @InjectMocks
    private lateinit var handler: PlatformExceptionHandler

    @Test
    fun `handle WorkeeException with INVALID_TOKEN should return UNAUTHORIZED status`() {
        // Given
        val exception = InvalidTokenException()
        val errorResponse = ErrorResponse("INVALID_TOKEN", "Token is invalid", "2023-01-01T10:00:00")
        whenever(mapper.asErrorResponse(exception)).thenReturn(errorResponse)

        // When
        val response = handler.handle(exception)

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)
        assertEquals(errorResponse, response.body)
        verify(mapper).asErrorResponse(exception)
    }

    @Test
    fun `handle WorkeeException with other error codes should return INTERNAL_SERVER_ERROR status`() {
        // Given
        val exception = ResourceNotAccessibleException()
        val errorResponse = ErrorResponse("RESOURCE_NOT_ACCESSIBLE", "Resource not accessible", "2023-01-01T10:00:00")
        whenever(mapper.asErrorResponse(exception)).thenReturn(errorResponse)

        // When
        val response = handler.handle(exception)

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
        assertEquals(errorResponse, response.body)
        verify(mapper).asErrorResponse(exception)
    }

    @Test
    fun `handle UnauthorizedUserException should return INTERNAL_SERVER_ERROR status`() {
        // Given
        val exception = UnauthorizedUserException()
        val errorResponse = ErrorResponse("UNAUTHORIZED_USER", "Unauthorized user", "2023-01-01T10:00:00")
        whenever(mapper.asErrorResponse(exception)).thenReturn(errorResponse)

        // When
        val response = handler.handle(exception)

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
        assertEquals(errorResponse, response.body)
        verify(mapper).asErrorResponse(exception)
    }

    @Test
    fun `handle AuthorizationDeniedException should return FORBIDDEN status`() {
        // Given
        val exception = AuthorizationDeniedException("Access denied")
        val errorResponse = ErrorResponse("ACCESS_DENIED", "Access Denied", "2023-01-01T10:00:00")
        whenever(mapper.asErrorResponse(ErrorCode.ACCESS_DENIED, "Access Denied")).thenReturn(errorResponse)

        // When
        val response = handler.handle(exception)

        // Then
        assertEquals(HttpStatus.FORBIDDEN, response.statusCode)
        assertEquals(errorResponse, response.body)
        verify(mapper).asErrorResponse(ErrorCode.ACCESS_DENIED, "Access Denied")
    }

    @Test
    fun `handle MissingServletRequestParameterException should return BAD_REQUEST status`() {
        // Given
        val exception = MissingServletRequestParameterException("username", "String")
        val errorResponse = ErrorResponse("BAD_REQUEST", "Bad Request", "2023-01-01T10:00:00")
        whenever(mapper.asErrorResponse(ErrorCode.BAD_REQUEST, "Bad Request")).thenReturn(errorResponse)

        // When
        val response = handler.handle(exception)

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals(errorResponse, response.body)
        verify(mapper).asErrorResponse(ErrorCode.BAD_REQUEST, "Bad Request")
    }

    @Test
    fun `handle generic Exception should return INTERNAL_SERVER_ERROR status`() {
        // Given
        val errorResponse = ErrorResponse("INTERNAL_SERVER_ERROR", "Internal Server Error", "2023-01-01T10:00:00")
        whenever(mapper.asErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Server Error")).thenReturn(errorResponse)

        // When
        val response = handler.handle()

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
        assertEquals(errorResponse, response.body)
        verify(mapper).asErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Server Error")
    }

    @Test
    fun `handle should delegate to mapper for all WorkeeException types`() {
        val testCases = listOf(
            InvalidTokenException(),
            ResourceNotAccessibleException(), 
            UnauthorizedUserException()
        )

        testCases.forEach { exception ->
            // Given
            val errorResponse = ErrorResponse(exception.code.toString(), exception.message, "2023-01-01T10:00:00")
            whenever(mapper.asErrorResponse(exception)).thenReturn(errorResponse)

            // When
            val response = handler.handle(exception)

            // Then
            assertNotNull(response)
            assertEquals(errorResponse, response.body)
            verify(mapper).asErrorResponse(exception)
        }
    }
}
