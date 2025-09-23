package com.workee.api.infrastructure.repository.email.postgres

import com.workee.api.domain.exception.DatabaseUnavailableException
import com.workee.api.domain.exception.EmailVerificationNotFoundException
import com.workee.api.domain.model.email.EmailVerification
import com.workee.api.domain.repository.email.EmailVerificationRepository
import com.workee.api.infrastructure.repository.email.EmailVerificationRepositoryMapper
import com.workee.api.infrastructure.repository.email.JpaEmailVerificationRepository

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class PostgresEmailVerificationRepositoryAdapter(
    private val jpaEmailVerificationRepository: JpaEmailVerificationRepository,
    private val emailVerificationRepositoryMapper: EmailVerificationRepositoryMapper
) : EmailVerificationRepository {

    companion object {
        private const val LOG_HEADER = "[POSTGRES EMAIL VERIFICATION REPOSITORY]"
        private val logger = LoggerFactory.getLogger(EmailVerificationRepository::class.java)
    }

    override fun findById(id: UUID): EmailVerification {
        try {
            val emailVerification =
                jpaEmailVerificationRepository.findById(id).orElseThrow { EmailVerificationNotFoundException() }
            return emailVerificationRepositoryMapper.asEmailVerification(emailVerification)
        } catch (ex: EmailVerificationNotFoundException) {
            throw ex
        } catch (ex: Exception) {
            logger.error("$LOG_HEADER -- Error finding email verification by id with id: $id. Error: $ex")
            throw DatabaseUnavailableException()
        }
    }

    override fun save(emailVerification: EmailVerification): EmailVerification {
        try {
            val emailVerificationEntity = emailVerificationRepositoryMapper.asEmailVerificationEntity(emailVerification)
            return emailVerificationRepositoryMapper.asEmailVerification(
                jpaEmailVerificationRepository.save(
                    emailVerificationEntity
                )
            )
        } catch (ex: Exception) {
            logger.error("$LOG_HEADER -- Error saving email verification. Error: $ex")
            throw DatabaseUnavailableException()
        }
    }

    override fun delete(id: UUID) {
        try {
            jpaEmailVerificationRepository.deleteById(id)
        } catch (ex: Exception) {
            logger.error("$LOG_HEADER -- Error deleting email verification with id: $id. Error: $ex")
            throw DatabaseUnavailableException()
        }
    }
}