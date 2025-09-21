package com.workee.api.infrastructure.rest.client.userprovider.keycloak

import com.workee.api.domain.exception.ResourceNotAccessibleException
import com.workee.api.domain.exception.UnauthorizedUserException
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
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

    fun getToken(username: String, password: String): String {
        val mediaType = "application/x-www-form-urlencoded".toMediaType()
        val bodyString =
            "grant_type=password&client_id=$clientId&username=$username&password=$password&client_secret=$clientSecret"
        val requestBody = bodyString.toRequestBody(mediaType)

        val request = Request.Builder()
            .url(tokenUrl)
            .post(requestBody)
            .build()

        client.newCall(request).execute().use { response ->
            when (response.code) {
                200 -> return response.body!!.string()
                401 -> throw UnauthorizedUserException()
                else -> throw ResourceNotAccessibleException()
            }
        }
    }
}