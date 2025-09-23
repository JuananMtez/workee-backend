package com.workee.api.domain.model.user

data class AuthTokenDTO (
    val accessToken: String,
    val expiresIn: Long,
    val refreshExpiresIn: Long,
    val refreshToken: String,
    val tokenType: String
)