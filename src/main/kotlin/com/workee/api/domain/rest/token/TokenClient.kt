package com.workee.api.domain.rest.token

interface TokenClient {

    fun getToken(username: String, password: String): String

}