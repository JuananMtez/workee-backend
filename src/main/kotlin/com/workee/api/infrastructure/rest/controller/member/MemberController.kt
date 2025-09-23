package com.workee.api.infrastructure.rest.controller.member

import com.workee.api.domain.service.member.MemberService
import com.workee.api.infrastructure.rest.controller.member.message.request.CreateMemberRequest
import com.workee.api.infrastructure.rest.controller.member.message.response.MemberResponse
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/member")
class MemberController(
    private val memberService: MemberService,
    private val memberControllerMapper: MemberControllerMapper
) {

    @PostMapping
    fun createMember(@Validated @RequestBody createMemberRequest: CreateMemberRequest): ResponseEntity<MemberResponse> {
        val createMemberDTO = memberControllerMapper.asCreateMemberDTO(createMemberRequest)
        val member = memberService.createMember(createMemberDTO)
        val memberResponse = memberControllerMapper.asMemberResponse(member)

        return ResponseEntity.ok(memberResponse)
    }

}