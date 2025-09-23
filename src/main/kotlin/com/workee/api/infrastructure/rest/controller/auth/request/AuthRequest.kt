package com.workee.api.infrastructure.rest.controller.auth.request

data class AuthRequest (
    val username: String,
    val password: String
)