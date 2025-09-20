package com.workee.api.infrastructure.repository.user

import com.workee.api.domain.model.User
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserSqlMapper {

    fun asUser(userEntity: UserEntity): User
    fun asUserEntity(user: User): UserEntity
}