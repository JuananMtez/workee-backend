package com.workee.api.application.user

import com.workee.api.domain.model.user.CreateUserDTO
import com.workee.api.domain.model.user.User
import com.workee.api.domain.model.userprovider.UserProviderDTO
import com.workee.api.domain.repository.user.UserRepository
import com.workee.api.domain.service.user.UserService
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class UserServiceAdapter(
    private val userRepository: UserRepository
): UserService {

    override fun createUser(
        createUserDTO: CreateUserDTO,
        userProviderDTO: UserProviderDTO
    ): User {
        var user = User(
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

        user = userRepository.saveUser(user)

        return user



    }

}