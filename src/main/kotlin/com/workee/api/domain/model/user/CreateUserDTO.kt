package com.workee.api.domain.model.user

data class CreateUserDTO(

    val username: String,

    val password: String,

    val name: String,

    val firstSurname: String,

    val secondSurname: String?,

    val email: String

)