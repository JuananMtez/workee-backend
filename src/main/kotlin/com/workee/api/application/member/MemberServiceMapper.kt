package com.workee.api.application.member

import com.workee.api.domain.model.member.CreateMemberDTO
import com.workee.api.domain.model.user.CreateUserDTO
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface MemberServiceMapper {

    fun asCreateUserDTO(createMemberDTO: CreateMemberDTO): CreateUserDTO
}