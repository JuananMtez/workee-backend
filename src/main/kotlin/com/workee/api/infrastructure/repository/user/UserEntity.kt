package com.workee.api.infrastructure.repository.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "user", schema = "public")
class UserEntity(

    @Id
    val id: UUID,

    @Column(name = "provider_id", nullable = false)
    val providerId: String,

    @Column(name = "nickname", nullable = false)
    val nickname: String,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "first_surname", nullable = false)
    val firstSurname: String,

    @Column(name = "second_surname", nullable = true)
    val secondSurname: String?,

    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "validated_email", nullable = false)
    val validatedEmail: Boolean,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime,

    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime
)