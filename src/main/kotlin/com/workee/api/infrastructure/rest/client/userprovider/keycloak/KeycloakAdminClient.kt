package com.workee.api.infrastructure.rest.client.userprovider.keycloak

import com.workee.api.domain.exception.KeycloakErrorException
import com.workee.api.domain.model.user.RoleEnum
import com.workee.api.infrastructure.rest.client.userprovider.keycloak.message.request.KeycloakCreateUserRequest
import com.workee.api.infrastructure.rest.client.userprovider.keycloak.message.response.KeycloakCreateUserResponse
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.keycloak.admin.client.resource.RealmResource
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class KeycloakAdminClient(
    @param:Value("\${workee.keycloak.base-url}") private val serverUrl: String,
    @param:Value("\${workee.keycloak.realm}") private val realm: String,
    @param:Value("\${workee.keycloak.realm-master}") private val realmMaster: String,
    @param:Value("\${workee.keycloak.admin-cli}") private val adminCli: String,
    @param:Value("\${workee.keycloak.user-console}") private val userConsole: String,
    @param:Value("\${workee.keycloak.password-console}") private val passwordConsole: String
) {

    companion object {
        private const val LOG_HEADER = "[KEYCLOAK ADMIN CLIENT]"
        private val logger = LoggerFactory.getLogger(KeycloakAdminClient::class.java)
    }

    private val keycloak: Keycloak = KeycloakBuilder.builder()
        .serverUrl(serverUrl)
        .realm(realmMaster)
        .clientId(adminCli)
        .username(userConsole)
        .password(passwordConsole)
        .build()

    fun getRealmResource(): RealmResource {
        return keycloak.realm(realm)
    }

    fun createUser(keycloakCreateUserRequest: KeycloakCreateUserRequest): KeycloakCreateUserResponse {

        val realmResource = getRealmResource()
        val usersResource = realmResource.users()

        val userRepresentation = UserRepresentation()
        userRepresentation.firstName = keycloakCreateUserRequest.firstName
        userRepresentation.lastName = keycloakCreateUserRequest.lastName
        userRepresentation.email = keycloakCreateUserRequest.email
        userRepresentation.username = keycloakCreateUserRequest.username
        userRepresentation.isEmailVerified = false
        userRepresentation.isEnabled = true

        try {
            val response = usersResource.create(userRepresentation)

            if (response.status == 201) {
                val path = response.location.path
                val userId = path.substring(path.lastIndexOf('/') + 1)

                val credentialRepresentation = CredentialRepresentation()
                credentialRepresentation.isTemporary = false
                credentialRepresentation.type = OAuth2Constants.PASSWORD
                credentialRepresentation.value = keycloakCreateUserRequest.password

                usersResource.get(userId).resetPassword(credentialRepresentation)

                return KeycloakCreateUserResponse(userId)
            }

        } catch (ex: Exception) {
            logger.error("$LOG_HEADER - Error while creating user with username ${keycloakCreateUserRequest.username} and email: ${keycloakCreateUserRequest.email}. Error: ${ex.message}")
            throw KeycloakErrorException()
        }

        throw KeycloakErrorException()
    }

    fun addRoleToUser(userProviderId: String, role: RoleEnum) {
        try {
            val realmResource = getRealmResource()
            val usersResource = realmResource.users()

            val roleRepresentations =
                listOf(realmResource.roles().get(role.value).toRepresentation())

            usersResource.get(userProviderId).roles().realmLevel().add(roleRepresentations)
        } catch (ex: Exception) {
            logger.error("$LOG_HEADER - Error while adding role to user with id ${userProviderId}. Error: ${ex.message}")
            throw KeycloakErrorException()
        }
    }

    fun updateEmailVerified(userProviderId: String) {
        try {
            val realmResource = getRealmResource()
            val usersResource = realmResource.users()
            val userRepresentation = usersResource.get(userProviderId).toRepresentation()
            userRepresentation.isEmailVerified = true
            usersResource.get(userProviderId).update(userRepresentation)

        } catch (ex: Exception) {
            logger.error("$LOG_HEADER - Error while verifying email update to user with id ${userProviderId}. Error: ${ex.message}")
            throw KeycloakErrorException()
        }
    }


}