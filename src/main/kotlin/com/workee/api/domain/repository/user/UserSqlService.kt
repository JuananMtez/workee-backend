package com.workee.api.domain.repository.user

import com.workee.api.domain.model.User
import java.util.UUID

interface UserSqlService {

    fun findUserById(id: UUID): User
}