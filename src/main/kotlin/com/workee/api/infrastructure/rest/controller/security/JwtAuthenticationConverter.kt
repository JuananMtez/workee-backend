package com.workee.api.infrastructure.rest.controller.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.stereotype.Component
import kotlin.collections.get
import kotlin.collections.plus

@Component
class JwtAuthenticationConverter: Converter<Jwt, AbstractAuthenticationToken> {

    private val jwtGrantedAuthoritiesConverter = JwtGrantedAuthoritiesConverter()

    @Value("\${jwt.auth.converter.principle-attribute}")
    private  lateinit var principleAttribute: String

    @Value("\${jwt.auth.converter.client-id}")
    private lateinit var clientId: String

    override fun convert(jwt: Jwt): AbstractAuthenticationToken {
        val authorities: Collection<GrantedAuthority> =
            (jwtGrantedAuthoritiesConverter.convert(jwt) ?: emptyList()) +
                    extractResourceRole(jwt)

        return JwtAuthenticationToken(jwt, authorities, getPrincipleName(jwt))
    }


    private fun extractResourceRole(jwt: Jwt): Collection<GrantedAuthority> {
        val resourceAccess = jwt.claims["resource_access"] as? Map<*, *> ?: return emptySet()
        val resource = resourceAccess[clientId] as? Map<*, *> ?: return emptySet()
        val roles = resource["roles"] as? List<*> ?: return emptySet()

        return roles.map { SimpleGrantedAuthority("ROLE_$it") }
    }

    private fun getPrincipleName(jwt: Jwt): String {
        return jwt.claims[principleAttribute].toString()

    }

}