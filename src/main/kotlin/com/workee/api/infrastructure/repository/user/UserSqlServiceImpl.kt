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

    private val logger = LoggerFactory.getLogger(UserSqlService::class.java)

    override fun findUserById(id: UUID): User {
        try {
            val user = userRepository.findById(id).orElseThrow { UserNotFoundException() }
            return userSqlMapper.asUser(user)
        } catch (ex: Exception) {
            logger.error("Error finding user by id with id: $id. Error: $ex")
            throw DatabaseUnavailableException()
        }
    }
}