package com.workee.api.infrastructure.repository.user.postgres

import com.workee.api.domain.exception.DatabaseUnavailableException
import com.workee.api.domain.exception.UserNotFoundException
import com.workee.api.domain.model.user.User
import com.workee.api.domain.repository.user.UserRepository
import com.workee.api.infrastructure.repository.user.JpaUserRepository
import com.workee.api.infrastructure.repository.user.UserRepositoryMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class PostgresUserRepositoryAdapter(
    private val jpaUserRepository: JpaUserRepository,
    private val userRepositoryMapper: UserRepositoryMapper
) : UserRepository {

    companion object {
        private const val LOG_HEADER = "[POSTGRES USER REPOSITORY]"
        private val logger = LoggerFactory.getLogger(UserRepository::class.java)
    }

    override fun findById(id: UUID): User {
        try {
            val user = jpaUserRepository.findById(id).orElseThrow { UserNotFoundException() }
            return userRepositoryMapper.asUser(user)
        } catch (ex: UserNotFoundException) {
            throw UserNotFoundException()
        } catch (ex: Exception) {
            logger.error("$LOG_HEADER -- Error finding user by id with id: $id. Error: $ex")
            throw DatabaseUnavailableException()
        }
    }

    override fun findByEmail(email: String): User {
        try {
            val user = jpaUserRepository.findByEmail(email).orElseThrow { UserNotFoundException() }
            return userRepositoryMapper.asUser(user)
        } catch (ex: UserNotFoundException) {
            throw UserNotFoundException()
        } catch (ex: Exception) {
            logger.error("$LOG_HEADER -- Error finding user by username with email: $email. Error: $ex")
            throw DatabaseUnavailableException()
        }
    }

    override fun findByUsername(username: String): User {
        try {
            val user = jpaUserRepository.findByUsername(username).orElseThrow { UserNotFoundException() }
            return userRepositoryMapper.asUser(user)
        } catch (ex: UserNotFoundException) {
            throw UserNotFoundException()
        } catch (ex: Exception) {
            logger.error("$LOG_HEADER -- Error finding user by username with username: $username. Error: $ex")
            throw DatabaseUnavailableException()
        }
    }

    override fun save(user: User): User {
        try {
            val userEntity = userRepositoryMapper.asCreateUserEntity(user)
            return userRepositoryMapper.asUser(jpaUserRepository.save(userEntity))
        } catch (ex: Exception) {
            logger.error("$LOG_HEADER -- Error saving user: $user. Error: $ex")
            throw DatabaseUnavailableException()
        }
    }

    override fun update(user: User): User {
        try {
            val userEntity = userRepositoryMapper.asUserEntity(user)
            return userRepositoryMapper.asUser(jpaUserRepository.save(userEntity))
        } catch (ex: Exception) {
            logger.error("$LOG_HEADER -- Error updating user: $user. Error: $ex")
            throw DatabaseUnavailableException()
        }
    }

    override fun deleteById(id: UUID) {
        try {
            jpaUserRepository.deleteById(id)
        } catch (ex: Exception) {
            logger.error("$LOG_HEADER -- Error deleting user by id: $id. Error: $ex")
            throw DatabaseUnavailableException()
        }
    }

    override fun existsByUsernameOrEmail(username: String, email: String): Boolean {
        try {
            return jpaUserRepository.existsByUsernameOrEmail(username, email)
        } catch (ex: Exception) {
            logger.error("$LOG_HEADER -- Error checking if exists user by username: $username or email: $email. Error: $ex")
            throw DatabaseUnavailableException()
        }
    }


}