package com.workee.api.domain.service.user

import com.workee.api.domain.model.User

interface UserService {

    fun createUser(user: User): User

}