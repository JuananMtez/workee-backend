package com.workee.api.domain.service.userprovider

import com.workee.api.domain.model.manager.CreateManagerDTO
import com.workee.api.domain.model.userprovider.UserProviderDTO

interface UserProviderService {

    fun getToken(username: String, password: String): String

    fun createManager(createManagerDTO: CreateManagerDTO): UserProviderDTO

}