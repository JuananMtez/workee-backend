package com.workee.api.domain.model.manager

import com.workee.api.domain.model.user.User
import java.time.LocalDateTime
import java.util.UUID

data class Manager (
    val id: UUID,
    val user: User,
    val taxIdentificationNumber: String,
    val billingAddress: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)