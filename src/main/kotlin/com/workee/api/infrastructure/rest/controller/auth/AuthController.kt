package com.workee.api.infrastructure.rest.controller.auth

import com.workee.api.domain.service.userprovider.UserProviderService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam


@RestController
@RequestMapping("/auth")
@Validated
class AuthController(private val tokensService: UserProviderService) {

    @PostMapping("/manager")
    fun loginManager(
        @RequestParam username: String,
        @RequestParam password: String
    ): ResponseEntity<String> {
        return ResponseEntity.ok(tokensService.getToken(username, password))
    }

    @PostMapping("/member")
    fun loginMember(
        @RequestParam username: String,
        @RequestParam password: String
    ): ResponseEntity<String> {
        return ResponseEntity.ok(tokensService.getToken(username, password))
    }

}