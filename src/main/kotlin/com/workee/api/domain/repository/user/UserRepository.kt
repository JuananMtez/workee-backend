package com.workee.api.domain.repository.user

import com.workee.api.domain.model.user.User
import java.util.UUID

interface UserRepository {

    fun findUserById(id: UUID): User

    fun saveUser(user: User): User

    fun updateUser(user: User): User

    fun deleteUserById(id: UUID)
}