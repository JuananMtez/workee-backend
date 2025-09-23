package com.workee.api.infrastructure.rest.controller.user

import com.workee.api.domain.service.user.UserService
import com.workee.api.infrastructure.rest.controller.user.message.request.CreateUserRequest
import com.workee.api.infrastructure.rest.controller.user.message.response.UserResponse
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService,
    private val userControllerMapper: UserControllerMapper
) {

    @PostMapping
    fun createUser(@Validated @RequestBody createUserRequest: CreateUserRequest): ResponseEntity<UserResponse> {
        val createUserDTO = userControllerMapper.asCreateUserDTO(createUserRequest)
        val user = userService.createUser(createUserDTO)
        val userResponse = userControllerMapper.asUserResponse(user)

        return ResponseEntity.ok(userResponse)
    }

    @GetMapping("verify-email")
    fun verifyEmail(@RequestParam code: UUID): ResponseEntity<String> {
        userService.verifyEmail(code)
        return ResponseEntity.ok("Email verified")

    }

}