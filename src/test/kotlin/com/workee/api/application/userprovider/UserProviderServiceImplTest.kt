package com.workee.api.application.userprovider

import com.workee.api.domain.rest.userprovider.UserProviderClient
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import org.mockito.kotlin.verify

@ExtendWith(MockitoExtension::class)
class UserProviderServiceImplTest {

    @Mock
    private lateinit var userProviderClient: UserProviderClient

    @InjectMocks
    private lateinit var service: UserProviderServiceImpl

    @Test
    fun `getToken should delegate to userProviderClient and return token`() {
        // Given
        val username = "testuser"
        val password = "testpass"
        val expectedToken = "test-token-123"
        
        whenever(userProviderClient.getToken(username, password)).thenReturn(expectedToken)

        // When
        val result = service.getToken(username, password)

        // Then
        assertEquals(expectedToken, result)
        verify(userProviderClient).getToken(username, password)
    }

    @Test
    fun `getToken should propagate exception from client`() {
        // Given
        val username = "testuser"
        val password = "wrongpass"
        val expectedException = RuntimeException("Authentication failed")
        
        whenever(userProviderClient.getToken(username, password)).thenThrow(expectedException)

        // When & Then
        val thrownException = assertThrows(RuntimeException::class.java) {
            service.getToken(username, password)
        }
        
        assertEquals("Authentication failed", thrownException.message)
        verify(userProviderClient).getToken(username, password)
    }

    @Test
    fun `getToken should handle empty credentials`() {
        // Given
        val username = ""
        val password = ""
        val expectedToken = "empty-token"
        
        whenever(userProviderClient.getToken(username, password)).thenReturn(expectedToken)

        // When
        val result = service.getToken(username, password)

        // Then
        assertEquals(expectedToken, result)
        verify(userProviderClient).getToken(username, password)
    }

    @Test
    fun `getToken should not modify credentials before passing to client`() {
        // Given
        val username = "testuser"
        val password = "testpass"
        val expectedToken = "token"
        
        whenever(userProviderClient.getToken(username, password)).thenReturn(expectedToken)

        // When
        service.getToken(username, password)

        // Then
        verify(userProviderClient).getToken(username, password)  // Exact same values
    }
}
