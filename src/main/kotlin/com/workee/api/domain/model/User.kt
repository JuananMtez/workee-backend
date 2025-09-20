package com.workee.api.domain.model

import java.time.LocalDateTime
import java.util.UUID

data class User(

    val id: UUID,

    val providerId: String,

    val nickname: String,

    val name: String,

    val firstSurname: String,

    val secondSurname: String?,

    val email: String,

    val validatedEmail: Boolean,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime
)