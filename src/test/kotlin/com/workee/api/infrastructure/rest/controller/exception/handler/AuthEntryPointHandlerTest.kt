package com.workee.api.infrastructure.rest.controller.exception.handler

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.workee.api.domain.exception.ErrorCode
import com.workee.api.infrastructure.rest.controller.exception.message.ErrorResponse
import com.workee.api.infrastructure.rest.controller.exception.message.mapper.ExceptionRestMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import org.mockito.kotlin.verify
import org.springframework.security.core.AuthenticationException
import java.io.PrintWriter
import java.io.StringWriter

@ExtendWith(MockitoExtension::class)
class AuthEntryPointHandlerTest {

    @Mock
    private lateinit var mapper: ExceptionRestMapper

    @Mock
    private lateinit var request: HttpServletRequest

    @Mock
    private lateinit var response: HttpServletResponse

    @Mock
    private lateinit var authenticationException: AuthenticationException

    @InjectMocks
    private lateinit var authEntryPointHandler: AuthEntryPointHandler

    @Test
    fun `commence should set correct response status and content type`() {
        // Given
        val errorResponse = ErrorResponse("INVALID_TOKEN", "Token invalid", "2023-01-01T10:00:00")
        val stringWriter = StringWriter()
        val printWriter = PrintWriter(stringWriter)
        
        whenever(mapper.asErrorResponse(ErrorCode.INVALID_TOKEN, "Token invalid")).thenReturn(errorResponse)
        whenever(response.writer).thenReturn(printWriter)

        // When
        authEntryPointHandler.commence(request, response, authenticationException)

        // Then
        verify(response).status = HttpServletResponse.SC_UNAUTHORIZED
        verify(response).contentType = "application/json"
        verify(mapper).asErrorResponse(ErrorCode.INVALID_TOKEN, "Token invalid")
        
        val expectedJson = jacksonObjectMapper().writeValueAsString(errorResponse)
        val actualJson = stringWriter.toString()
        assertEquals(expectedJson, actualJson)
    }

    @Test
    fun `commence should write correct JSON response to writer`() {
        // Given
        val errorResponse = ErrorResponse("INVALID_TOKEN", "Token invalid", "2023-01-01T10:00:00")
        val stringWriter = StringWriter()
        val printWriter = PrintWriter(stringWriter)
        
        whenever(mapper.asErrorResponse(ErrorCode.INVALID_TOKEN, "Token invalid")).thenReturn(errorResponse)
        whenever(response.writer).thenReturn(printWriter)

        // When
        authEntryPointHandler.commence(request, response, authenticationException)

        // Then
        val expectedJson = jacksonObjectMapper().writeValueAsString(errorResponse)
        val actualJson = stringWriter.toString()
        assertEquals(expectedJson, actualJson)
        
        verify(mapper).asErrorResponse(ErrorCode.INVALID_TOKEN, "Token invalid")
    }

    @Test
    fun `commence should handle different authentication exceptions consistently`() {
        // Given
        val customAuthException = object : AuthenticationException("Custom auth error") {}
        val errorResponse = ErrorResponse("INVALID_TOKEN", "Token invalid", "2023-01-01T10:00:00")
        val stringWriter = StringWriter()
        val printWriter = PrintWriter(stringWriter)
        
        whenever(mapper.asErrorResponse(ErrorCode.INVALID_TOKEN, "Token invalid")).thenReturn(errorResponse)
        whenever(response.writer).thenReturn(printWriter)

        // When
        authEntryPointHandler.commence(request, response, customAuthException)

        // Then
        verify(response).status = HttpServletResponse.SC_UNAUTHORIZED
        verify(response).contentType = "application/json"
        
        // The handler should always use "Token invalid" message regardless of the actual exception message
        verify(mapper).asErrorResponse(ErrorCode.INVALID_TOKEN, "Token invalid")
    }

    @Test
    fun `commence should always use consistent error message`() {
        // Given
        val errorResponse = ErrorResponse("INVALID_TOKEN", "Token invalid", "2023-01-01T10:00:00")
        val stringWriter = StringWriter()
        val printWriter = PrintWriter(stringWriter)
        
        whenever(mapper.asErrorResponse(ErrorCode.INVALID_TOKEN, "Token invalid")).thenReturn(errorResponse)
        whenever(response.writer).thenReturn(printWriter)

        // When
        authEntryPointHandler.commence(request, response, authenticationException)

        // Then
        verify(mapper).asErrorResponse(ErrorCode.INVALID_TOKEN, "Token invalid")
        // Should not use any other error message
    }

    @Test
    fun `commence should handle writer operations correctly`() {
        // Given
        val errorResponse = ErrorResponse("INVALID_TOKEN", "Token invalid", "2023-01-01T10:00:00")
        val stringWriter = StringWriter()
        val printWriter = PrintWriter(stringWriter)
        
        whenever(mapper.asErrorResponse(ErrorCode.INVALID_TOKEN, "Token invalid")).thenReturn(errorResponse)
        whenever(response.writer).thenReturn(printWriter)

        // When
        authEntryPointHandler.commence(request, response, authenticationException)

        // Then
        verify(response).writer
        val writtenContent = stringWriter.toString()
        assertTrue(writtenContent.isNotEmpty())
        assertTrue(writtenContent.contains("INVALID_TOKEN"))
        assertTrue(writtenContent.contains("Token invalid"))
    }
}
