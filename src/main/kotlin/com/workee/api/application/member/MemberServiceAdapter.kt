package com.workee.api.application.member

import com.workee.api.domain.model.member.CreateMemberDTO
import com.workee.api.domain.model.member.Member
import com.workee.api.domain.repository.member.MemberRepository
import com.workee.api.domain.service.member.MemberService
import org.springframework.stereotype.Service

@Service
class MemberServiceAdapter(
    private val memberRepository: MemberRepository,
    private val memberServiceMapper: MemberServiceMapper
) : MemberService {

    override fun createMember(createMemberDTO: CreateMemberDTO): Member {
        return TODO("Provide the return value")
    }
}