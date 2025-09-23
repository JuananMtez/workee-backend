package com.workee.api.domain.restclient.userprovider

import com.workee.api.domain.model.user.CreateUserDTO
import com.workee.api.domain.model.user.RoleEnum
import com.workee.api.domain.model.userprovider.UserProviderDTO

interface UserProviderClient {

    fun getToken(username: String, password: String): String
    fun createUser(createUserDTO: CreateUserDTO): UserProviderDTO
    fun addRoleToUser(userProviderId: String, role: RoleEnum)
    fun updateValidatedEmail(userProviderId: String)
}