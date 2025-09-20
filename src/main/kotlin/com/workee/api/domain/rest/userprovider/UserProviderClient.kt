package com.workee.api.domain.rest.userprovider

import com.workee.api.domain.model.User

interface UserProviderClient {

    fun getToken(username: String, password: String): String

    fun createUser(user: User): User

}