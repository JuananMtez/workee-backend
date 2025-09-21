package com.workee.api.domain.service.manager

import com.workee.api.domain.model.manager.CreateManagerDTO
import com.workee.api.domain.model.manager.Manager

interface ManagerService {

    fun createManager(createManagerDTO: CreateManagerDTO): Manager
}