package com.workee.api.domain.repository.manager

import com.workee.api.domain.model.manager.Manager
import java.util.UUID

interface ManagerRepository {

    fun findById(id: UUID): Manager
    
    fun existsByUsernameOrEmail(email: String, username: String): Boolean

    fun save(manager: Manager): Manager

    fun update(manager: Manager): Manager

    fun deleteById(id: UUID)
}