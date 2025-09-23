package com.workee.api.infrastructure.rest.controller.auth.request

import com.fasterxml.jackson.annotation.JsonProperty

data class RefreshTokenRequest(
    @field:JsonProperty(value = "refresh_token")
    val refreshToken: String
)