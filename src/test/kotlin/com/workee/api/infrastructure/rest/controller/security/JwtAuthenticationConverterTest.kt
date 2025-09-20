package com.workee.api.infrastructure.rest.controller.security

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.test.util.ReflectionTestUtils

class JwtAuthenticationConverterTest {

    private val converter = JwtAuthenticationConverter()

    @BeforeEach
    fun setup() {
        ReflectionTestUtils.setField(converter, "principleAttribute", "sub")
        ReflectionTestUtils.setField(converter, "clientId", "workee-app")
    }

    @Test
    fun `convert should create JwtAuthenticationToken with correct principal`() {
        // Given
        val expectedPrincipal = "user123"
        val headers = mapOf("alg" to "RS256")
        val claims = mapOf<String, Any>(
            "sub" to expectedPrincipal,
            "resource_access" to emptyMap<String, Any>()
        )
        val jwt = Jwt.withTokenValue("token")
            .headers { it.putAll(headers) }
            .claims { it.putAll(claims) }
            .build()

        // When
        val result = converter.convert(jwt)

        // Then
        assertNotNull(result)
        assertTrue(result is JwtAuthenticationToken)
        assertEquals(expectedPrincipal, result.name)
        assertEquals(jwt, (result as JwtAuthenticationToken).token)
    }

    @Test
    fun `convert should extract resource roles correctly`() {
        // Given
        val principalName = "user123"
        val roles = listOf("admin", "user")
        val resourceAccess = mapOf(
            "workee-app" to mapOf(
                "roles" to roles
            )
        )
        val headers = mapOf("alg" to "RS256")
        val claims = mapOf<String, Any>(
            "sub" to principalName,
            "resource_access" to resourceAccess
        )
        val jwt = Jwt.withTokenValue("token")
            .headers { it.putAll(headers) }
            .claims { it.putAll(claims) }
            .build()

        // When
        val result = converter.convert(jwt) as JwtAuthenticationToken

        // Then
        val authorities = result.authorities
        assertTrue(authorities.contains(SimpleGrantedAuthority("ROLE_admin")))
        assertTrue(authorities.contains(SimpleGrantedAuthority("ROLE_user")))
    }

    @Test
    fun `convert should handle missing resource_access claim`() {
        // Given
        val principalName = "user123"
        val headers = mapOf("alg" to "RS256")
        val claims = mapOf<String, Any>(
            "sub" to principalName
        )
        val jwt = Jwt.withTokenValue("token")
            .headers { it.putAll(headers) }
            .claims { it.putAll(claims) }
            .build()

        // When
        val result = converter.convert(jwt) as JwtAuthenticationToken

        // Then
        assertNotNull(result)
        assertEquals(principalName, result.name)
        // Should not throw exception even without resource_access
    }

    @Test
    fun `convert should handle missing client in resource_access`() {
        // Given
        val principalName = "user123"
        val resourceAccess = mapOf(
            "other-client" to mapOf(
                "roles" to listOf("admin")
            )
        )
        val headers = mapOf("alg" to "RS256")
        val claims = mapOf<String, Any>(
            "sub" to principalName,
            "resource_access" to resourceAccess
        )
        val jwt = Jwt.withTokenValue("token")
            .headers { it.putAll(headers) }
            .claims { it.putAll(claims) }
            .build()

        // When
        val result = converter.convert(jwt) as JwtAuthenticationToken

        // Then
        assertNotNull(result)
        assertEquals(principalName, result.name)
        // Should not contain roles from other client
        val roleAuthorities = result.authorities.filter { it.authority.startsWith("ROLE_") }
        assertTrue(roleAuthorities.isEmpty())
    }

    @Test
    fun `convert should handle missing roles in resource`() {
        // Given
        val principalName = "user123"
        val resourceAccess = mapOf(
            "workee-app" to mapOf<String, Any>()
        )
        val headers = mapOf("alg" to "RS256")
        val claims = mapOf<String, Any>(
            "sub" to principalName,
            "resource_access" to resourceAccess
        )
        val jwt = Jwt.withTokenValue("token")
            .headers { it.putAll(headers) }
            .claims { it.putAll(claims) }
            .build()

        // When
        val result = converter.convert(jwt) as JwtAuthenticationToken

        // Then
        assertNotNull(result)
        assertEquals(principalName, result.name)
        val roleAuthorities = result.authorities.filter { it.authority.startsWith("ROLE_") }
        assertTrue(roleAuthorities.isEmpty())
    }

    @Test
    fun `convert should handle empty roles list`() {
        // Given
        val principalName = "user123"
        val resourceAccess = mapOf(
            "workee-app" to mapOf(
                "roles" to emptyList<String>()
            )
        )
        val headers = mapOf("alg" to "RS256")
        val claims = mapOf<String, Any>(
            "sub" to principalName,
            "resource_access" to resourceAccess
        )
        val jwt = Jwt.withTokenValue("token")
            .headers { it.putAll(headers) }
            .claims { it.putAll(claims) }
            .build()

        // When
        val result = converter.convert(jwt) as JwtAuthenticationToken

        // Then
        assertNotNull(result)
        assertEquals(principalName, result.name)
        val roleAuthorities = result.authorities.filter { it.authority.startsWith("ROLE_") }
        assertTrue(roleAuthorities.isEmpty())
    }

    @Test
    fun `convert should add ROLE_ prefix to each role`() {
        // Given
        val principalName = "user123"
        val roles = listOf("manager", "employee", "admin")
        val resourceAccess = mapOf(
            "workee-app" to mapOf(
                "roles" to roles
            )
        )
        val headers = mapOf("alg" to "RS256")
        val claims = mapOf<String, Any>(
            "sub" to principalName,
            "resource_access" to resourceAccess
        )
        val jwt = Jwt.withTokenValue("token")
            .headers { it.putAll(headers) }
            .claims { it.putAll(claims) }
            .build()

        // When
        val result = converter.convert(jwt) as JwtAuthenticationToken

        // Then
        val authorities = result.authorities
        assertTrue(authorities.contains(SimpleGrantedAuthority("ROLE_manager")))
        assertTrue(authorities.contains(SimpleGrantedAuthority("ROLE_employee")))
        assertTrue(authorities.contains(SimpleGrantedAuthority("ROLE_admin")))
        assertEquals(3, authorities.filter { it.authority.startsWith("ROLE_") }.size)
    }
}
