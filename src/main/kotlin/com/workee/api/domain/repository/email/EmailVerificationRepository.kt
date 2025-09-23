package com.workee.api.domain.repository.email

import com.workee.api.domain.model.email.EmailVerification
import java.util.UUID

interface EmailVerificationRepository {

    fun findById(id: UUID): EmailVerification
    fun save(emailVerification: EmailVerification): EmailVerification
    fun delete(id: UUID)


}