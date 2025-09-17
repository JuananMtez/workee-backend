package com.workee.api.application.token

import com.workee.api.domain.rest.token.TokenClient
import com.workee.api.domain.service.TokenService
import org.springframework.stereotype.Service

@Service
class TokenServiceImpl(
    private val tokenClient: TokenClient,
): TokenService {
    override fun getToken(username: String, password: String): String {
        return tokenClient.getToken(username, password)


    }


}