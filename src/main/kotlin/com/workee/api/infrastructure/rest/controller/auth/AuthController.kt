package com.workee.api.infrastructure.rest.controller.auth

import com.workee.api.domain.service.userprovider.UserProviderService
import org.springframework.http.*
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
@Validated
class AuthController(private val tokensService: UserProviderService) {

    @PostMapping("/login")
    fun login(
        @RequestParam username: String,
        @RequestParam password: String
    ): ResponseEntity<String> {
        return ResponseEntity.ok(tokensService.getToken(username, password))

    }
}