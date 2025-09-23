package com.workee.api.infrastructure.rest.controller.auth

import com.workee.api.domain.service.token.AuthTokenService
import com.workee.api.infrastructure.rest.controller.auth.request.AuthRequest
import com.workee.api.infrastructure.rest.controller.auth.response.AuthTokenResponse
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/auth")
@Validated
class AuthController(
    private val authTokenService: AuthTokenService,
    private val authControllerMapper: AuthControllerMapper,
) {

    @PostMapping("/token")
    fun getToken(
        @Validated @RequestBody authRequest: AuthRequest, response: HttpServletResponse
    ): ResponseEntity<AuthTokenResponse> {
        val authTokenResponse = authControllerMapper.asAuthTokenResponse(
            authTokenService.getToken(authRequest.username, authRequest.password)
        )

        val cookie = Cookie("refreshToken", authTokenResponse.refreshToken).apply {
            isHttpOnly = true
            secure = true
            maxAge = 7 * 24 * 60 * 60
            path = "/"
        }
        response.addCookie(cookie)

        return ResponseEntity.ok(authTokenResponse)
    }

    @GetMapping("/refresh-token")
    fun refreshToken(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ResponseEntity<AuthTokenResponse> {

        val refreshToken = request.cookies
            ?.firstOrNull { it.name == "refreshToken" }
            ?.value

        val authTokenResponse = authControllerMapper.asAuthTokenResponse(
            authTokenService.refreshToken(refreshToken)
        )

        val newCookie = Cookie("refreshToken", authTokenResponse.refreshToken).apply {
            path = "/"
            maxAge = 7 * 24 * 60 * 60
            isHttpOnly = true
            secure = true
        }
        response.addCookie(newCookie)

        return ResponseEntity.ok(authTokenResponse)


    }
}