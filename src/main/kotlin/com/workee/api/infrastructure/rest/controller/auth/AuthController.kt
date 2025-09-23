package com.workee.api.infrastructure.rest.controller.auth

import com.workee.api.domain.service.user.UserService
import com.workee.api.infrastructure.rest.controller.auth.request.AuthRequest
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/auth")
@Validated
class AuthController(private val userService: UserService) {

    @PostMapping
    fun login(
        @Validated @RequestBody authRequest: AuthRequest
    ): ResponseEntity<String> {
        return ResponseEntity.ok(userService.getToken(authRequest.username, authRequest.password))
    }
}