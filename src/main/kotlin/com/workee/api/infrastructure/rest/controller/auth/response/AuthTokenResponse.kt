package com.workee.api.infrastructure.rest.controller.auth.response

import com.fasterxml.jackson.annotation.JsonProperty

data class AuthTokenResponse (
    @field:JsonProperty("access_token")
    val accessToken: String,

    @field:JsonProperty("expires_in")
    val expiresIn: Long,

    @field:JsonProperty("refresh_expires_in")
    val refreshExpiresIn: Long,

    @field:JsonProperty("refresh_token")
    val refreshToken: String,

    @field:JsonProperty("token_type")
    val tokenType: String
)