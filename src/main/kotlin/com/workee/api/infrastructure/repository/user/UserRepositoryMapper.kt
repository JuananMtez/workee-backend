package com.workee.api.infrastructure.repository.user

import com.workee.api.domain.model.user.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserRepositoryMapper {

    fun asUser(userEntity: UserEntity): User

    fun asUserEntity(user: User): UserEntity

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    fun asCreateUserEntity(user: User): UserEntity

}