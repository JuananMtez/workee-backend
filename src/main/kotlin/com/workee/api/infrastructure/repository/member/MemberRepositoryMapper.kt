package com.workee.api.infrastructure.repository.member

import com.workee.api.domain.model.member.Member
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface MemberRepositoryMapper {

    fun asMember(memberEntity: MemberEntity): Member

    fun asMemberEntity(member: Member): MemberEntity

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    fun asCreateMemberEntity(member: Member): MemberEntity

}