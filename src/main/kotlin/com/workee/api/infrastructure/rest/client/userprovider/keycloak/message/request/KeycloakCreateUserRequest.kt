package com.workee.api.infrastructure.rest.client.userprovider.keycloak.message.request

data class KeycloakCreateUserRequest (
    val firstName: String,
    val lastName: String,
    val email: String,
    val username: String,
    val password: String
)