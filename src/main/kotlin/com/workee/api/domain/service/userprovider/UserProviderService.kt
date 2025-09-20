package com.workee.api.domain.service.userprovider

interface UserProviderService {

    fun getToken(username: String, password: String): String
}