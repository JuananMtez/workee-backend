package com.workee.api.infrastructure.rest.client.userprovider.keycloak

import com.workee.api.domain.model.userprovider.UserProviderDTO
import com.workee.api.domain.restclient.userprovider.UserProviderClient
import com.workee.api.infrastructure.rest.client.userprovider.keycloak.message.request.KeycloakCreateUserRequest

import org.springframework.stereotype.Service

@Service
class KeycloakAdapter(
    private val keycloakAdminClient: KeycloakAdminClient,
    private val keycloakTokenClient: KeycloakTokenClient,
    private val keycloakMapper: KeycloakMapper
) : UserProviderClient {

    override fun getToken(username: String, password: String): String {
        return keycloakTokenClient.getToken(username, password)
    }

    override fun createManager(keycloakCreateUserRequest: KeycloakCreateUserRequest): UserProviderDTO {
        val keycloakCreateUserResponse = keycloakAdminClient.createUser(keycloakCreateUserRequest)
        val userProviderDTO = keycloakMapper.asUserProviderDTO(keycloakCreateUserResponse)
        return userProviderDTO
    }

}