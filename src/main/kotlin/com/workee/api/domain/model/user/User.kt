package com.workee.api.domain.model.user

import java.time.LocalDateTime
import java.util.UUID

data class User(

    var id: UUID,

    val providerId: String,

    val username: String,

    val name: String,

    val firstSurname: String,

    val secondSurname: String?,

    val email: String,

    val validatedEmail: Boolean,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime
)