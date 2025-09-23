package com.workee.api.infrastructure.rest.controller.member

import com.workee.api.domain.model.member.CreateMemberDTO
import com.workee.api.domain.model.member.Member
import com.workee.api.infrastructure.rest.controller.member.message.request.CreateMemberRequest
import com.workee.api.infrastructure.rest.controller.member.message.response.MemberResponse
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface MemberControllerMapper {

    fun asCreateMemberDTO(createMemberRequest: CreateMemberRequest): CreateMemberDTO
    fun asMemberResponse(member: Member): MemberResponse

}