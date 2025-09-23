package com.workee.api.domain.model.email

import com.workee.api.domain.model.user.User
import java.time.LocalDateTime
import java.util.UUID

data class EmailVerification (
    val id: UUID,
    val user: User,
    val createdAt: LocalDateTime
)