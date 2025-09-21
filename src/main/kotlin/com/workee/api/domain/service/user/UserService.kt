package com.workee.api.domain.service.user

import com.workee.api.domain.model.user.CreateUserDTO
import com.workee.api.domain.model.user.User
import com.workee.api.domain.model.userprovider.UserProviderDTO

interface UserService {

    fun createUser(createUserDTO: CreateUserDTO, userProviderDTO: UserProviderDTO): User
}