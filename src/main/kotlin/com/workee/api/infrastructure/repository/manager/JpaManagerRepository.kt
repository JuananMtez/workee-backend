package com.workee.api.infrastructure.repository.manager

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface JpaManagerRepository : JpaRepository<ManagerEntity, UUID> {

    fun existsByUserEmail(email: String): Boolean
    fun existsByUserUsername(username: String): Boolean
    fun existsByUserProviderId(providerId: String): Boolean

}