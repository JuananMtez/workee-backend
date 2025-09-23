package com.workee.api.infrastructure.rest.client.userprovider.keycloak

import com.fasterxml.jackson.databind.ObjectMapper
import com.workee.api.domain.exception.ResourceNotAccessibleException
import com.workee.api.domain.exception.UnauthorizedUserException
import com.workee.api.infrastructure.rest.client.userprovider.keycloak.message.response.KeycloakTokenResponse
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class KeycloakTokenClient {

    @Value("\${workee.keycloak.token-url}")
    private lateinit var tokenUrl: String

    @Value("\${workee.keycloak.client-secret}")
    private lateinit var clientSecret: String

    @Value("\${workee.keycloak.client-id}")
    private lateinit var clientId: String

    private val client: OkHttpClient = OkHttpClient()
    private val objectMapper = ObjectMapper()

    fun getToken(username: String, password: String): KeycloakTokenResponse {

        val formBody = FormBody.Builder()
            .add("grant_type", "password")
            .add("client_secret", clientSecret)
            .add("client_id", clientId)
            .add("username", username)
            .add("password", password)
            .build()

        val request = Request.Builder()
            .url(tokenUrl)
            .post(formBody)
            .build()

        return sendRequest(request)

    }

    fun refreshToken(refreshToken: String): KeycloakTokenResponse {
        val formBody = FormBody.Builder()
            .add("grant_type", "refresh_token")
            .add("client_secret", clientSecret)
            .add("client_id", clientId)
            .add("refresh_token", refreshToken)
            .build()

        val request = Request.Builder()
            .url(tokenUrl)
            .post(formBody)
            .build()

        return sendRequest(request)
    }

    private fun sendRequest(request: Request): KeycloakTokenResponse {
        client.newCall(request).execute().use { response ->
            when (response.code) {
                200 ->
                    return objectMapper.readValue(
                        response.body!!.string(),
                        KeycloakTokenResponse::class.java
                    )

                401 -> throw UnauthorizedUserException()
                else -> {
                    throw ResourceNotAccessibleException()
                }
            }
        }
    }
}