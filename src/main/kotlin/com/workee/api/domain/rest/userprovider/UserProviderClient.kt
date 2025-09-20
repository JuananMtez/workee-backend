package com.workee.api.domain.rest.userprovider

interface UserProviderClient {

    fun getToken(username: String, password: String): String

}