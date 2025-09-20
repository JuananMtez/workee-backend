package com.workee.api.infrastructure.rest.controller.exception.message

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class ErrorResponseTest {

    @Test
    fun `ErrorResponse should be created with all parameters`() {
        // Given
        val code = "INVALID_TOKEN"
        val message = "Token is invalid"
        val timestamp = "2023-01-01T10:00:00"

        // When
        val errorResponse = ErrorResponse(code, message, timestamp)

        // Then
        assertEquals(code, errorResponse.code)
        assertEquals(message, errorResponse.message)
        assertEquals(timestamp, errorResponse.timestamp)
    }

    @Test
    fun `ErrorResponse should handle empty strings`() {
        // Given
        val code = ""
        val message = ""
        val timestamp = ""

        // When
        val errorResponse = ErrorResponse(code, message, timestamp)

        // Then
        assertEquals(code, errorResponse.code)
        assertEquals(message, errorResponse.message)
        assertEquals(timestamp, errorResponse.timestamp)
    }

    @Test
    fun `ErrorResponse should maintain field values unchanged`() {
        // Given
        val code = "ACCESS_DENIED"
        val message = "Access is denied for this resource"
        val timestamp = "2023-12-25T15:30:45"

        // When
        val errorResponse = ErrorResponse(code, message, timestamp)

        // Then
        assertEquals(code, errorResponse.code)
        assertEquals(message, errorResponse.message)
        assertEquals(timestamp, errorResponse.timestamp)
    }

    @Test
    fun `ErrorResponse should handle special characters in fields`() {
        // Given
        val code = "SPECIAL_ERROR"
        val message = "Error with special chars: áéíóú, @#$%^&*()"
        val timestamp = "2023-01-01T10:00:00.123Z"

        // When
        val errorResponse = ErrorResponse(code, message, timestamp)

        // Then
        assertEquals(code, errorResponse.code)
        assertEquals(message, errorResponse.message)
        assertEquals(timestamp, errorResponse.timestamp)
    }

    @Test
    fun `ErrorResponse fields should be accessible`() {
        // Given
        val errorResponse = ErrorResponse("CODE", "MESSAGE", "TIME")

        // Then
        assertNotNull(errorResponse.code)
        assertNotNull(errorResponse.message)
        assertNotNull(errorResponse.timestamp)
    }

    @Test
    fun `ErrorResponse should handle null-like string values`() {
        // Given
        val code = "null"
        val message = "null"
        val timestamp = "null"

        // When
        val errorResponse = ErrorResponse(code, message, timestamp)

        // Then
        assertEquals("null", errorResponse.code)
        assertEquals("null", errorResponse.message)
        assertEquals("null", errorResponse.timestamp)
    }
}
