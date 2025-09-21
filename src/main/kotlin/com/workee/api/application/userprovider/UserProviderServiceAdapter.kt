package com.workee.api.application.userprovider

import com.workee.api.domain.model.manager.CreateManagerDTO
import com.workee.api.domain.model.user.RoleEnum
import com.workee.api.domain.model.userprovider.UserProviderDTO
import com.workee.api.domain.restclient.userprovider.UserProviderClient
import com.workee.api.domain.service.userprovider.UserProviderService
import com.workee.api.infrastructure.rest.client.userprovider.keycloak.message.request.KeycloakCreateUserRequest
import org.springframework.stereotype.Service

@Service
class UserProviderServiceAdapter(
    private val userProviderClient: UserProviderClient,
) : UserProviderService {

    override fun getToken(username: String, password: String): String {
        return userProviderClient.getToken(username, password)
    }

    override fun createManager(createManagerDTO: CreateManagerDTO): UserProviderDTO {

        val keycloakCreateUserRequest = KeycloakCreateUserRequest(
            username = createManagerDTO.username,
            firstName = createManagerDTO.name,
            lastName = createManagerDTO.name + (createManagerDTO.secondSurname?.let { " $it" } ?: ""),
            email = createManagerDTO.email,
            password = createManagerDTO.password,
            role = RoleEnum.MANAGER

        )

        val keycloakCreateUserResponse = userProviderClient.createManager(keycloakCreateUserRequest)

        val userProviderDTO = UserProviderDTO(
            keycloakCreateUserResponse.userId
        )

        return userProviderDTO

    }


}