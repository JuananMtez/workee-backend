package com.workee.api.infrastructure.rest.controller.manager

import com.workee.api.domain.model.manager.CreateManagerDTO
import com.workee.api.domain.model.manager.Manager
import com.workee.api.infrastructure.rest.controller.manager.message.request.CreateManagerRequest
import com.workee.api.infrastructure.rest.controller.manager.message.response.ManagerResponse
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface ManagerControllerMapper {

    fun asCreateManagerDTO(createManagerRequest: CreateManagerRequest): CreateManagerDTO
    fun asManagerResponse(manager: Manager): ManagerResponse

}