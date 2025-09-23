package com.workee.api.infrastructure.rest.controller.auth.response

data class AuthTokenResponse (
    val accessToken: String,
    val expiresIn: Long,
    val refreshExpiresIn: Long,
    val refreshToken: String,
    val tokenType: String
)