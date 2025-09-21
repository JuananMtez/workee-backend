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

    override fun findUserById(id: UUID): User {
        try {
            val user = jpaUserRepository.findById(id).orElseThrow { UserNotFoundException() }
            return userRepositoryMapper.asUser(user)
        } catch (ex: Exception) {
            logger.error("$LOG_HEADER -- Error finding user by id with id: $id. Error: $ex")
            throw DatabaseUnavailableException()
        }
    }

    override fun saveUser(user: User): User {
        try {
            val userEntity = userRepositoryMapper.asCreateUserEntity(user)
            return userRepositoryMapper.asUser(jpaUserRepository.save(userEntity))
        } catch (ex: Exception) {
            logger.error("$LOG_HEADER -- Error saving user: $user. Error: $ex")
            throw DatabaseUnavailableException()
        }
    }

    override fun updateUser(user: User): User {
        try {
            val userEntity = userRepositoryMapper.asUserEntity(user)
            return userRepositoryMapper.asUser(jpaUserRepository.save(userEntity))
        } catch (ex: Exception) {
            logger.error("$LOG_HEADER -- Error updating user: $user. Error: $ex")
            throw DatabaseUnavailableException()
        }
    }

    override fun deleteUserById(id: UUID) {
        try {
            jpaUserRepository.deleteById(id)
        } catch (ex: Exception) {
            logger.error("$LOG_HEADER -- Error deleting user by id: $id. Error: $ex")
            throw DatabaseUnavailableException()
        }
    }
}