package com.workee.api.infrastructure.rest.controller.auth

import com.workee.api.domain.exception.UnauthorizedUserException
import com.workee.api.domain.service.userprovider.UserProviderService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import org.mockito.kotlin.verify
import org.springframework.http.HttpStatus

@ExtendWith(MockitoExtension::class)
class AuthControllerTest {

    @Mock
    private lateinit var userProviderService: UserProviderService

    @InjectMocks
    private lateinit var controller: AuthController

    @Test
    fun `login should return OK response with token when credentials are valid`() {
        // Given
        val username = "testuser"
        val password = "testpass"
        val expectedToken = "test-token-123"
        
        whenever(userProviderService.getToken(username, password)).thenReturn(expectedToken)

        // When
        val response = controller.login(username, password)

        // Then
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(expectedToken, response.body)
        verify(userProviderService).getToken(username, password)
    }

    @Test
    fun `login should propagate UnauthorizedUserException when credentials are invalid`() {
        // Given
        val username = "testuser"
        val password = "wrongpass"
        
        whenever(userProviderService.getToken(username, password)).thenThrow(UnauthorizedUserException())

        // When & Then
        assertThrows(UnauthorizedUserException::class.java) {
            controller.login(username, password)
        }
        
        verify(userProviderService).getToken(username, password)
    }

    @Test
    fun `login should propagate other exceptions from service`() {
        // Given
        val username = "testuser"
        val password = "testpass"
        val expectedException = RuntimeException("Service error")
        
        whenever(userProviderService.getToken(username, password)).thenThrow(expectedException)

        // When & Then
        val thrownException = assertThrows(RuntimeException::class.java) {
            controller.login(username, password)
        }
        
        assertEquals("Service error", thrownException.message)
        verify(userProviderService).getToken(username, password)
    }

    @Test
    fun `login should handle empty credentials`() {
        // Given
        val username = ""
        val password = ""
        val expectedToken = "default-token"
        
        whenever(userProviderService.getToken(username, password)).thenReturn(expectedToken)

        // When
        val response = controller.login(username, password)

        // Then
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(expectedToken, response.body)
        verify(userProviderService).getToken(username, password)
    }

    @Test
    fun `login should not modify credentials before calling service`() {
        // Given
        val originalUsername = "testuser"
        val originalPassword = "testpass"
        val expectedToken = "token"
        
        whenever(userProviderService.getToken(originalUsername, originalPassword)).thenReturn(expectedToken)

        // When
        controller.login(originalUsername, originalPassword)

        // Then
        verify(userProviderService).getToken(originalUsername, originalPassword)
    }
}
