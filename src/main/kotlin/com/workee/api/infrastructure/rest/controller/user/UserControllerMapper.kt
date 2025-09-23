package com.workee.api.infrastructure.rest.controller.user

import com.workee.api.domain.model.user.CreateUserDTO
import com.workee.api.domain.model.user.User
import com.workee.api.infrastructure.rest.controller.user.message.request.CreateUserRequest
import com.workee.api.infrastructure.rest.controller.user.message.response.UserResponse
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface UserControllerMapper {

    fun asCreateUserDTO(createUserRequest: CreateUserRequest): CreateUserDTO
    fun asUserResponse(user: User): UserResponse

}