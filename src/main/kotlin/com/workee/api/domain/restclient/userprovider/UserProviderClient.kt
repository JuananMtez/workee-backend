package com.workee.api.domain.restclient.userprovider

import com.workee.api.domain.model.userprovider.UserProviderDTO
import com.workee.api.infrastructure.rest.client.userprovider.keycloak.message.request.KeycloakCreateUserRequest

interface UserProviderClient {

    fun getToken(username: String, password: String): String
    fun createManager(keycloakCreateUserRequest: KeycloakCreateUserRequest): UserProviderDTO




}