package com.workee.api.infrastructure.repository.user

import com.workee.api.domain.exception.DatabaseUnavailableException
import com.workee.api.domain.exception.UserNotFoundException
import com.workee.api.domain.model.User
import com.workee.api.domain.repository.user.UserSqlService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class UserSqlServiceImpl(
    private val userRepository: UserRepository,
    private val userSqlMapper: UserSqlMapper
) : UserSqlService {

    companion object {
        private const val LOG_HEADER = "[USER SQL SERVICE]"
        private val logger = LoggerFactory.getLogger(UserSqlService::class.java)

    }

    override fun findUserById(id: UUID): User {
        try {
            val user = userRepository.findById(id).orElseThrow { UserNotFoundException() }
            return userSqlMapper.asUser(user)
        } catch (ex: Exception) {
            logger.error("$LOG_HEADER -- Error finding user by id with id: $id. Error: $ex")
            throw DatabaseUnavailableException()
        }
    }

    override fun saveUser(user: User): User {
        try {
            val userEntity = userSqlMapper.asCreateUserEntity(user)
            return userSqlMapper.asUser(userRepository.save(userEntity))
        } catch (ex: Exception) {
            logger.error("$LOG_HEADER -- Error saving user: $user. Error: $ex")
            throw DatabaseUnavailableException()
        }
    }

    override fun updateUser(user: User): User {
        try {
            val userEntity = userSqlMapper.asUserEntity(user)
            return userSqlMapper.asUser(userRepository.save(userEntity))
        } catch (ex: Exception) {
            logger.error("$LOG_HEADER -- Error updating user: $user. Error: $ex")
            throw DatabaseUnavailableException()
        }
    }

    override fun deleteUserById(id: UUID) {
        try {
            userRepository.deleteById(id)
        } catch (ex: Exception) {
            logger.error("$LOG_HEADER -- Error deleting user by id: $id. Error: $ex")
            throw DatabaseUnavailableException()
        }
    }
}