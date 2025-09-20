package com.workee.api.domain.exception

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class WorkeeExceptionTest {

    @Test
    fun `InvalidTokenException should have correct properties`() {
        // When
        val exception = InvalidTokenException()

        // Then
        assertEquals(ErrorCode.INVALID_TOKEN, exception.code)
        assertEquals("Token is invalid", exception.message)
        assertTrue(exception is WorkeeException)
        assertTrue(exception is RuntimeException)
    }

    @Test
    fun `ResourceNotAccessibleException should have correct properties`() {
        // When
        val exception = ResourceNotAccessibleException()

        // Then
        assertEquals(ErrorCode.RESOURCE_NOT_ACCESSIBLE, exception.code)
        assertEquals("Resource not accessible", exception.message)
        assertTrue(exception is WorkeeException)
        assertTrue(exception is RuntimeException)
    }

    @Test
    fun `UnauthorizedUserException should have correct properties`() {
        // When
        val exception = UnauthorizedUserException()

        // Then
        assertEquals(ErrorCode.UNAUTHORIZED_USER, exception.code)
        assertEquals("Unauthorized user", exception.message)
        assertTrue(exception is WorkeeException)
        assertTrue(exception is RuntimeException)
    }

    @Test
    fun `WorkeeException should inherit from RuntimeException`() {
        // Given
        val exception = InvalidTokenException()

        // Then
        assertTrue(exception is RuntimeException)
        assertNotNull(exception.message)
        assertNotNull(exception.code)
    }
}
