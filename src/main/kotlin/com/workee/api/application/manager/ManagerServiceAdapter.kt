package com.workee.api.application.manager

import com.workee.api.domain.exception.ManagerAlreadyExistsException
import com.workee.api.domain.model.manager.CreateManagerDTO
import com.workee.api.domain.model.manager.Manager
import com.workee.api.domain.model.user.RoleEnum
import com.workee.api.domain.repository.manager.ManagerRepository
import com.workee.api.domain.restclient.userprovider.UserProviderClient
import com.workee.api.domain.service.manager.ManagerService
import com.workee.api.domain.service.user.UserService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class ManagerServiceAdapter(
    private val managerRepository: ManagerRepository,
    private val userService: UserService,
    private val userProviderClient: UserProviderClient
) : ManagerService {
    override fun createManager(createManagerDTO: CreateManagerDTO): Manager {

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as Jwt
        val providerId = principal.claims["sub"] as String

        if (managerRepository.existsByProviderId(providerId)) {
            throw ManagerAlreadyExistsException()
        }

        val user = userService.findByProviderId(providerId)

        userProviderClient.addRoleToUser(user.providerId, RoleEnum.MANAGER)

        var manager = Manager(
            id = UUID.randomUUID(),
            user = user,
            taxIdentificationNumber = createManagerDTO.taxIdentificationNumber,
            billingAddress = createManagerDTO.billingAddress,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
        )

        manager = managerRepository.save(manager)

        return manager

    }
}