package com.workee.api.infrastructure.rest.auth

import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

@RestController
@RequestMapping("/auth")
class AuthController {

    private val restTemplate = RestTemplate()

    @PostMapping("/login")
    fun login(
        @RequestParam username: String,
        @RequestParam password: String
    ): ResponseEntity<String> {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED

        val body = "grant_type=password&client_id=workee-client-api-rest&username=$username&password=$password&client_secret=7BNVk5x3z0TXNcb1ZyPBfio6drZ42Bei"

        val request = HttpEntity(body, headers)
        try {
            val response = restTemplate.postForEntity(
                "http://localhost:9090/realms/workee-realm-dev/protocol/openid-connect/token",
                request,
                String::class.java
            )
            return response

        } catch (e: RestClientException) {
            e.printStackTrace()
        }
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}