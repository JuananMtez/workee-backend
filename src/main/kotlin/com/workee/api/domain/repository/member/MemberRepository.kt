package com.workee.api.domain.repository.member

import com.workee.api.domain.model.member.Member
import java.util.UUID

interface MemberRepository {

    fun findById(id: UUID): Member
    
    fun existsByUsernameOrEmail(email: String, username: String): Boolean

    fun save(member: Member): Member

    fun update(member: Member): Member

    fun deleteById(id: UUID)
}