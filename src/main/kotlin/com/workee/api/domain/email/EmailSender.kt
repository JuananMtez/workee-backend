package com.workee.api.domain.email

import com.workee.api.domain.model.user.User
import java.util.UUID

interface EmailSender {
    fun sendEmail(email: String, subject: String, body: String)

    fun sendVerifyEmail(user: User, emailVerificationCode: UUID)
}