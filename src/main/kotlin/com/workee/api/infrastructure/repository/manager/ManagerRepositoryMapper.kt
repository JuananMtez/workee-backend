package com.workee.api.infrastructure.repository.manager

import com.workee.api.domain.model.manager.Manager
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface ManagerRepositoryMapper {

    fun asManager(managerEntity: ManagerEntity): Manager

    fun asManagerEntity(manager: Manager): ManagerEntity

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    fun asCreateManagerEntity(manager: Manager): ManagerEntity

}