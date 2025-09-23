package com.workee.api.application.token

import com.workee.api.domain.exception.EmailNotVerifiedException
import com.workee.api.domain.exception.RefreshTokenNotFoundException
import com.workee.api.domain.exception.UserNotFoundException
import com.workee.api.domain.model.user.AuthTokenDTO
import com.workee.api.domain.model.user.User
import com.workee.api.domain.repository.user.UserRepository
import com.workee.api.domain.restclient.userprovider.UserProviderClient
import com.workee.api.domain.service.token.AuthTokenService
import org.springframework.stereotype.Service

@Service
class AuthTokenAdapter(
    private val userRepository: UserRepository,
    private val userProviderClient: UserProviderClient
): AuthTokenService {

    override fun getToken(username: String, password: String): AuthTokenDTO {
        val user: User = try {
            userRepository.findByUsername(username)
        } catch (_: UserNotFoundException) {
            userRepository.findByEmail(username)
        }

        if (user.validatedEmail) {
            return userProviderClient.getToken(username, password)
        }
        throw EmailNotVerifiedException()
    }

    override fun refreshToken(refreshToken: String?): AuthTokenDTO {
        if (refreshToken == null) {
            throw RefreshTokenNotFoundException()
        }

        return userProviderClient.refreshToken(refreshToken)

    }
}