package com.workee.api.infrastructure.rest.client.userprovider.keycloak

import com.workee.api.domain.model.user.AuthTokenDTO
import com.workee.api.domain.model.user.CreateUserDTO
import com.workee.api.domain.model.user.RoleEnum
import com.workee.api.domain.model.userprovider.UserProviderDTO
import com.workee.api.domain.restclient.userprovider.UserProviderClient
import com.workee.api.infrastructure.rest.client.userprovider.keycloak.message.request.KeycloakCreateUserRequest
import org.springframework.stereotype.Component

@Component
class KeycloakAdapter(
    private val keycloakAdminClient: KeycloakAdminClient,
    private val keycloakTokenClient: KeycloakTokenClient,
    private val keycloakMapper: KeycloakMapper
) : UserProviderClient {

    override fun getToken(username: String, password: String): AuthTokenDTO {
        return keycloakMapper.asAuthTokenDTO(
            keycloakTokenClient.getToken(username, password)
        )
    }

    override fun refreshToken(refreshToken: String): AuthTokenDTO {
        return keycloakMapper.asAuthTokenDTO(
            keycloakTokenClient.refreshToken(refreshToken)
        )
    }

    override fun createUser(createUserDTO: CreateUserDTO): UserProviderDTO {

        val keycloakCreateUserRequest = KeycloakCreateUserRequest(
            username = createUserDTO.username,
            firstName = createUserDTO.name,
            lastName = createUserDTO.firstSurname + (createUserDTO.secondSurname?.let { " $it" } ?: ""),
            email = createUserDTO.email,
            password = createUserDTO.password,
        )

        val keycloakCreateUserResponse = keycloakAdminClient.createUser(keycloakCreateUserRequest)
        val userProviderDTO = keycloakMapper.asUserProviderDTO(keycloakCreateUserResponse)
        return userProviderDTO
    }

    override fun addRoleToUser(userProviderId: String, role: RoleEnum) {
        keycloakAdminClient.addRoleToUser(userProviderId, role)
    }

    override fun updateValidatedEmail(userProviderId: String) {
        keycloakAdminClient.updateEmailVerified(userProviderId)
    }


}