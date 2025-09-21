package com.workee.api.infrastructure.repository.member

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface JpaMemberRepository : JpaRepository<MemberEntity, UUID> {

    fun existsByUserEmail(email: String): Boolean
    fun existsByUserUsername(username: String): Boolean

}