package com.workee.api.domain.model.member

import com.workee.api.domain.model.user.User
import java.time.LocalDateTime
import java.util.UUID

data class Member (
    val id: UUID,
    val user: User,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)