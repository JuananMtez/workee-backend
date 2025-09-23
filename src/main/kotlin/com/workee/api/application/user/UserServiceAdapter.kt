package com.workee.api.application.user

import com.workee.api.domain.email.EmailSender
import com.workee.api.domain.exception.EmailNotVerifiedException
import com.workee.api.domain.exception.UserNotFoundException
import com.workee.api.domain.exception.UsernameOrEmailAlreadyExistsException
import com.workee.api.domain.model.email.EmailVerification
import com.workee.api.domain.model.user.CreateUserDTO
import com.workee.api.domain.model.user.User
import com.workee.api.domain.repository.email.EmailVerificationRepository
import com.workee.api.domain.repository.user.UserRepository
import com.workee.api.domain.restclient.userprovider.UserProviderClient
import com.workee.api.domain.service.user.UserService
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class UserServiceAdapter(
    private val userRepository: UserRepository,
    private val userProviderClient: UserProviderClient,
    private val emailSender: EmailSender,
    private val emailVerificationRepository: EmailVerificationRepository
) : UserService {

    override fun getToken(username: String, password: String): String {
        val user: User = try {
            userRepository.findByUsername(username)
        } catch (ex: UserNotFoundException) {
            userRepository.findByEmail(username)
        }

        if (user.validatedEmail) {
            return userProviderClient.getToken(username, password)
        }
        throw EmailNotVerifiedException()
    }

    override fun createUser(createUserDTO: CreateUserDTO): User {

        if (existsByUsernameOrEmail(createUserDTO.username, createUserDTO.email)) {
            throw UsernameOrEmailAlreadyExistsException()
        }

        val userProviderDTO = userProviderClient.createUser(createUserDTO)

        val user = userRepository.save(
            User(
                id = UUID.randomUUID(),
                providerId = userProviderDTO.userId,
                username = createUserDTO.username,
                name = createUserDTO.name,
                firstSurname = createUserDTO.firstSurname,
                secondSurname = createUserDTO.secondSurname,
                validatedEmail = false,
                email = createUserDTO.email,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        )
        
        val emailVerification = EmailVerification(
            UUID.randomUUID(),
            user,
            LocalDateTime.now()
        )
        
        emailVerificationRepository.save(emailVerification)
        
        emailSender.sendVerifyEmail(user, emailVerification.id)

        return user

    }

    override fun existsByUsernameOrEmail(username: String, email: String): Boolean {
        return userRepository.existsByUsernameOrEmail(username, email)
    }

    override fun findByUsername(username: String): User {
        return userRepository.findByUsername(username)
    }

    override fun verifyEmail(code: UUID): User {
        
        val emailVerification = emailVerificationRepository.findById(code)
        
        val user = emailVerification.user

        userProviderClient.updateValidatedEmail(user.providerId)

        var userUpdated = user.copy(validatedEmail = true)
        userUpdated = userRepository.update(userUpdated)

        emailVerificationRepository.delete(code)

        return userUpdated
    }

}