package com.workee.api.infrastructure.rest.controller.auth

import com.workee.api.domain.service.token.AuthTokenService
import com.workee.api.infrastructure.rest.controller.auth.request.RefreshTokenRequest
import com.workee.api.infrastructure.rest.controller.auth.request.TokenRequest
import com.workee.api.infrastructure.rest.controller.auth.response.AuthTokenResponse
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/api/v1/auth")
@Validated
class AuthController(
    private val authTokenService: AuthTokenService,
    private val authControllerMapper: AuthControllerMapper,
) {

    @PostMapping("/token")
    fun getToken(@Validated @RequestBody tokenRequest: TokenRequest): ResponseEntity<AuthTokenResponse> {
        val authTokenResponse = authControllerMapper.asAuthTokenResponse(
            authTokenService.getToken(tokenRequest.username, tokenRequest.password)
        )
        return ResponseEntity.ok(authTokenResponse)
    }

    @PostMapping("/refresh")
    fun refreshToken(@Validated @RequestBody refreshTokenRequest: RefreshTokenRequest): ResponseEntity<AuthTokenResponse> {

        val authTokenResponse = authControllerMapper.asAuthTokenResponse(
            authTokenService.refreshToken(refreshTokenRequest.refreshToken)
        )

        return ResponseEntity.ok(authTokenResponse)

    }
}