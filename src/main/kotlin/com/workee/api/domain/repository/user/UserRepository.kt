package com.workee.api.domain.repository.user

import com.workee.api.domain.model.user.User
import java.util.UUID

interface UserRepository {

    fun findById(id: UUID): User

    fun findByUsername(username: String): User

    fun findByEmail(email: String): User

    fun findByProviderId(providerId: String): User

    fun save(user: User): User

    fun update(user: User): User

    fun deleteById(id: UUID)

    fun existsByUsernameOrEmail(username: String, email: String): Boolean
}