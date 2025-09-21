package com.workee.api.infrastructure.rest.client.userprovider.keycloak.message.request

import com.workee.api.domain.model.user.RoleEnum

data class KeycloakCreateUserRequest (
    val firstName: String,
    val lastName: String,
    val email: String,
    val username: String,
    val role: RoleEnum,
    val password: String
)