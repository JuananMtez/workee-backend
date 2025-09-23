package com.workee.api.infrastructure.rest.controller.user.message.response

import com.fasterxml.jackson.annotation.JsonProperty

data class UserResponse(

    val username: String,

    val name: String,

    @field:JsonProperty(value = "first_surname")
    val firstSurname: String,

    @field:JsonProperty(value = "second_surname")
    val secondSurname: String?,

    val email: String,

    @field:JsonProperty(value = "validated_email")
    val validatedEmail: Boolean
)