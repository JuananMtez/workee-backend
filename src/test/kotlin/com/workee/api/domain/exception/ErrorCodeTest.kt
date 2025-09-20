package com.workee.api.domain.exception

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class ErrorCodeTest {

    @Test
    fun `ErrorCode enum should contain all expected values`() {
        val expectedCodes = setOf(
            ErrorCode.INVALID_TOKEN,
            ErrorCode.INTERNAL_SERVER_ERROR,
            ErrorCode.ACCESS_DENIED,
            ErrorCode.RESOURCE_NOT_ACCESSIBLE,
            ErrorCode.UNAUTHORIZED_USER,
            ErrorCode.BAD_REQUEST
        )

        val actualCodes = ErrorCode.entries.toSet()

        assertEquals(expectedCodes, actualCodes)
        assertEquals(6, ErrorCode.entries.size)
    }

    @Test
    fun `ErrorCode toString should return correct string representation`() {
        assertEquals("INVALID_TOKEN", ErrorCode.INVALID_TOKEN.toString())
        assertEquals("INTERNAL_SERVER_ERROR", ErrorCode.INTERNAL_SERVER_ERROR.toString())
        assertEquals("ACCESS_DENIED", ErrorCode.ACCESS_DENIED.toString())
        assertEquals("RESOURCE_NOT_ACCESSIBLE", ErrorCode.RESOURCE_NOT_ACCESSIBLE.toString())
        assertEquals("UNAUTHORIZED_USER", ErrorCode.UNAUTHORIZED_USER.toString())
        assertEquals("BAD_REQUEST", ErrorCode.BAD_REQUEST.toString())
    }

    @Test
    fun `ErrorCode values should be accessible`() {
        // Test that each error code can be accessed
        assertNotNull(ErrorCode.INVALID_TOKEN)
        assertNotNull(ErrorCode.INTERNAL_SERVER_ERROR)
        assertNotNull(ErrorCode.ACCESS_DENIED)
        assertNotNull(ErrorCode.RESOURCE_NOT_ACCESSIBLE)
        assertNotNull(ErrorCode.UNAUTHORIZED_USER)
        assertNotNull(ErrorCode.BAD_REQUEST)
    }
}
