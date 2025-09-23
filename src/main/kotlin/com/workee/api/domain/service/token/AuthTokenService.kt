package com.workee.api.domain.service.token

import com.workee.api.domain.model.user.AuthTokenDTO

interface AuthTokenService {
    fun getToken(username: String, password: String): AuthTokenDTO
    fun refreshToken(refreshToken: String?): AuthTokenDTO
}