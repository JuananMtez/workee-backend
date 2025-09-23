package com.workee.api.infrastructure.rest.controller.member.message.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateMemberRequest(

    @field:NotBlank(message = "Username cannot be blank")
    val username: String,

    @field:NotBlank(message = "Name cannot be blank")
    val name: String,

    @field:NotBlank(message = "First surname cannot be blank")
    val firstSurname: String,

    val secondSurname: String? = null,

    @field:Email(message = "Invalid email format")
    @field:NotBlank(message = "Email cannot be blank")
    val email: String,

    @field:Size(min = 8, message = "Password must be at least 8 characters long")
    val password: String

)