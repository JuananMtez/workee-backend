package com.workee.api.domain.service.member

import com.workee.api.domain.model.member.CreateMemberDTO
import com.workee.api.domain.model.member.Member

interface MemberService {

    fun createMember(createMemberDTO: CreateMemberDTO): Member
}