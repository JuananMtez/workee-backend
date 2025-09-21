package com.workee.api.domain.model.manager

data class CreateManagerDTO(

    val username: String,

    val name: String,

    val password: String,

    val firstSurname: String,

    val secondSurname: String?,

    val email: String

)