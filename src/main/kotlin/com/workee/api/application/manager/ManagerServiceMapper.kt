package com.workee.api.application.manager

import com.workee.api.domain.model.manager.CreateManagerDTO
import com.workee.api.domain.model.user.CreateUserDTO
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface ManagerServiceMapper {

    fun asCreateUserDTO(createManager: CreateManagerDTO): CreateUserDTO
}