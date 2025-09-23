package com.workee.api.infrastructure.rest.controller.auth

import com.workee.api.domain.model.user.AuthTokenDTO
import com.workee.api.infrastructure.rest.controller.auth.response.AuthTokenResponse
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface AuthControllerMapper {

    fun asAuthTokenResponse(authTokenDTO: AuthTokenDTO): AuthTokenResponse
}