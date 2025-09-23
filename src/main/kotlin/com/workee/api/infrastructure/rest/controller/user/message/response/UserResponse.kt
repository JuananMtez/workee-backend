package com.workee.api.infrastructure.rest.controller.user.message.response

data class UserResponse(

    val username: String,

    val name: String,

    val firstSurname: String,

    val secondSurname: String?,

    val email: String,

    val validatedEmail: Boolean
)