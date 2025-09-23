package com.workee.api.infrastructure.smtp

import com.workee.api.domain.email.EmailSender
import com.workee.api.domain.exception.SmtpException
import com.workee.api.domain.model.user.User
import jakarta.mail.Authenticator
import jakarta.mail.Message
import jakarta.mail.MessagingException
import jakarta.mail.PasswordAuthentication
import jakarta.mail.Session
import jakarta.mail.Transport
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Properties
import java.util.UUID

@Component
class SmtpEmailSender: EmailSender {

    companion object {
        private const val LOG_HEADER = "[SMTP EMAIL SENDER]"
        private val logger = LoggerFactory.getLogger(SmtpEmailSender::class.java)
    }

    @Value("\${workee.email.smtp.host}")
    private lateinit var host: String

    @Value("\${workee.email.smtp.port}")
    private lateinit var port: String

    @Value("\${workee.email.smtp.username}")
    private lateinit var username: String

    @Value("\${workee.email.smtp.password}")
    private lateinit var password: String


    override fun sendEmail(email: String, subject: String, body: String) {
        val props = Properties().apply {
            put("mail.smtp.auth", "true")
            put("mail.smtp.starttls.enable", "true")
            put("mail.smtp.host", host)
            put("mail.smtp.port", port)
        }
        val session = Session.getInstance(props, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(username, password)

            }
        })

        try {
            val message = MimeMessage(session).apply {
                setFrom(InternetAddress(username))
                setRecipients(Message.RecipientType.TO, InternetAddress.parse(email))
                this.subject = subject
                setText(body)
            }

            Transport.send(message)

        } catch (e: MessagingException) {
            logger.error("$LOG_HEADER: Error sending email. Error: ${e.message}")
            throw SmtpException()
        }
    }

    override fun sendVerifyEmail(user: User, emailVerificationCode: UUID) {
        val subject = "Please validate you email account"
        val body = "Hello, ${user.name} ${user.firstSurname}, validate your account clicking the link: http://localhost:8080/user/verify-email?code=$emailVerificationCode"

        sendEmail(user.email, subject, body)
    }
}