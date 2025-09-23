package com.workee.api.infrastructure.rest.controller.auth.request

data class TokenRequest (
    val username: String,
    val password: String
)