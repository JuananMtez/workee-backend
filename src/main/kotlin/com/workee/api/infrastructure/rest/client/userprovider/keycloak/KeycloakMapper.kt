package com.workee.api.infrastructure.rest.client.userprovider.keycloak

import com.workee.api.domain.model.userprovider.UserProviderDTO
import com.workee.api.infrastructure.rest.client.userprovider.keycloak.message.response.KeycloakCreateUserResponse
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface KeycloakMapper {

    fun asUserProviderDTO(keycloakCreateUserResponse: KeycloakCreateUserResponse): UserProviderDTO
}