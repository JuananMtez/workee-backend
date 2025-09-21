package com.workee.api.domain.model.member

data class CreateMemberDTO(

    val username: String,

    val name: String,

    val password: String,

    val firstSurname: String,

    val secondSurname: String?,

    val email: String

)