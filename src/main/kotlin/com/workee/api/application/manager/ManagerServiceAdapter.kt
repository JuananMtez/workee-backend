package com.workee.api.application.manager

import com.workee.api.domain.exception.UsernameOrEmailAlreadyExistsException
import com.workee.api.domain.model.manager.CreateManagerDTO
import com.workee.api.domain.model.manager.Manager
import com.workee.api.domain.repository.manager.ManagerRepository
import com.workee.api.domain.service.manager.ManagerService
import com.workee.api.domain.service.user.UserService
import com.workee.api.domain.service.userprovider.UserProviderService
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class ManagerServiceAdapter(
    private val managerRepository: ManagerRepository,
    private val userProviderService: UserProviderService,
    private val userService: UserService,
    private val managerServiceMapper: ManagerServiceMapper
) : ManagerService {

    override fun createManager(createManagerDTO: CreateManagerDTO): Manager {

        if (managerRepository.existsByUsernameOrEmail(createManagerDTO.email, createManagerDTO.username)) {
            throw UsernameOrEmailAlreadyExistsException()
        }

        val userProviderDTO = userProviderService.createManager(createManagerDTO)

        val createUserDTO = managerServiceMapper.asCreateUserDTO(createManagerDTO)

        val user = userService.createUser(createUserDTO, userProviderDTO)

        var manager = Manager(
            id = UUID.randomUUID(),
            user = user,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        manager = managerRepository.save(manager)

        return manager

    }
}