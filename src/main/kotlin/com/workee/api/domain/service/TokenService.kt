package com.workee.api.domain.service

interface TokenService {

    fun getToken(username: String, password: String): String
}