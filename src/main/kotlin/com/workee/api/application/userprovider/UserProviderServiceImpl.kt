package com.workee.api.application.userprovider

import com.workee.api.domain.rest.userprovider.UserProviderClient
import com.workee.api.domain.service.userprovider.UserProviderService
import org.springframework.stereotype.Service

@Service
class UserProviderServiceImpl(
    private val userProviderClient: UserProviderClient,
): UserProviderService {
    override fun getToken(username: String, password: String): String {
        return userProviderClient.getToken(username, password)
    }


}