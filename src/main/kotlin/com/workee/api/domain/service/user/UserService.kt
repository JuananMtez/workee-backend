package com.workee.api.domain.service.user

import com.workee.api.domain.model.user.CreateUserDTO
import com.workee.api.domain.model.user.User
import java.util.UUID

interface UserService {

    fun createUser(createUserDTO: CreateUserDTO): User

    fun existsByUsernameOrEmail(username: String, email: String): Boolean

    fun findByUsername(username: String): User

    fun findByProviderId(providerId: String): User

    fun verifyEmail(code: UUID): User

}