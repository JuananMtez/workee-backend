package com.workee.api.infrastructure.rest.client.userprovider.keycloak.message.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class KeycloakTokenResponse(
    @field:JsonProperty("access_token")
    var accessToken: String? = null,

    @field:JsonProperty("expires_in")
    var expiresIn: Long? = null,

    @field:JsonProperty("refresh_expires_in")
    var refreshExpiresIn: Long? = null,

    @field:JsonProperty("refresh_token")
    var refreshToken: String? = null,

    @field:JsonProperty("token_type")
    var tokenType: String? = null,
)