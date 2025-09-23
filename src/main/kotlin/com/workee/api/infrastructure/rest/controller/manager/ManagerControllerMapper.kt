package com.workee.api.infrastructure.rest.controller.manager

import com.workee.api.domain.model.manager.CreateManagerDTO
import com.workee.api.domain.model.manager.Manager
import com.workee.api.infrastructure.rest.controller.manager.message.request.CreateManagerRequest
import com.workee.api.infrastructure.rest.controller.manager.message.response.ManagerResponse
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface ManagerControllerMapper {

    fun asCreateManagerDTO(createManagerRequest: CreateManagerRequest): CreateManagerDTO

    @Mapping(target = "username", source = "manager.user.username")
    @Mapping(target = "name", source = "manager.user.name")
    @Mapping(target = "firstSurname", source = "manager.user.firstSurname")
    @Mapping(target = "secondSurname", source = "manager.user.secondSurname")
    @Mapping(target = "email", source = "manager.user.email")
    fun asManagerResponse(manager: Manager): ManagerResponse

}