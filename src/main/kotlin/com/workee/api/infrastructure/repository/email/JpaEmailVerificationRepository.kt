package com.workee.api.infrastructure.repository.email

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface JpaEmailVerificationRepository : JpaRepository<EmailVerificationEntity, UUID>