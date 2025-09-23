package com.workee.api.infrastructure.rest.client.userprovider.keycloak

import com.workee.api.domain.model.user.AuthTokenDTO
import com.workee.api.domain.model.userprovider.UserProviderDTO
import com.workee.api.infrastructure.rest.client.userprovider.keycloak.message.response.KeycloakCreateUserResponse
import com.workee.api.infrastructure.rest.client.userprovider.keycloak.message.response.KeycloakTokenResponse
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface KeycloakMapper {
    fun asAuthTokenDTO(keycloakTokenResponse: KeycloakTokenResponse): AuthTokenDTO
    fun asUserProviderDTO(keycloakCreateUserResponse: KeycloakCreateUserResponse): UserProviderDTO
}