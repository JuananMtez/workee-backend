package com.workee.api.infrastructure.repository.user

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface JpaUserRepository : JpaRepository<UserEntity, UUID> {
    fun existsByUsernameOrEmail(username: String, email: String): Boolean
    fun findByUsername(username: String): Optional<UserEntity>
    fun findByEmail(email: String): Optional<UserEntity>
}