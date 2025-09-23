package com.workee.api.infrastructure.repository.manager.postgres

import com.workee.api.domain.exception.DatabaseUnavailableException
import com.workee.api.domain.exception.UserNotFoundException
import com.workee.api.domain.model.manager.Manager
import com.workee.api.domain.repository.manager.ManagerRepository
import com.workee.api.infrastructure.repository.manager.JpaManagerRepository
import com.workee.api.infrastructure.repository.manager.ManagerRepositoryMapper

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class PostgresManagerRepositoryAdapter(
    private val jpaManagerRepository: JpaManagerRepository,
    private val managerRepositoryMapper: ManagerRepositoryMapper
) : ManagerRepository {

    companion object {
        private const val LOG_HEADER = "[POSTGRES MANAGER REPOSITORY]"
        private val logger = LoggerFactory.getLogger(ManagerRepository::class.java)
    }

    override fun findById(id: UUID): Manager {
        try {
            val manager = jpaManagerRepository.findById(id).orElseThrow { UserNotFoundException() }
            return managerRepositoryMapper.asManager(manager)
        } catch (ex: UserNotFoundException) {
            throw ex
        } catch (ex: Exception) {
            logger.error("$LOG_HEADER -- Error finding manager by id with id: $id. Error: $ex")
            throw DatabaseUnavailableException()
        }
    }

    override fun existsByUsernameOrEmail(email: String, username: String): Boolean {
        try {
            return jpaManagerRepository.existsByUserEmail(email) || jpaManagerRepository.existsByUserUsername(username)
        } catch (ex: Exception) {
            logger.error("$LOG_HEADER -- Error checking if exists by email: $email or username: $username. Error: $ex")
            throw DatabaseUnavailableException()
        }
    }

    override fun existsByUsername(username: String): Boolean {
        try {
            return jpaManagerRepository.existsByUserUsername(username)
        } catch (ex: Exception) {
            logger.error("$LOG_HEADER -- Error checking if exists by username: $username. Error: $ex")
            throw DatabaseUnavailableException()
        }
    }

    override fun save(manager: Manager): Manager {
        try {
            val managerEntity = managerRepositoryMapper.asCreateManagerEntity(manager)
            return managerRepositoryMapper.asManager(jpaManagerRepository.save(managerEntity))
        } catch (ex: Exception) {
            logger.error("$LOG_HEADER -- Error saving manager: $manager. Error: $ex")
            throw DatabaseUnavailableException()
        }
    }

    override fun update(manager: Manager): Manager {
        try {
            val managerEntity = managerRepositoryMapper.asManagerEntity(manager)
            return managerRepositoryMapper.asManager(jpaManagerRepository.save(managerEntity))
        } catch (ex: Exception) {
            logger.error("$LOG_HEADER -- Error updating manager: $manager. Error: $ex")
            throw DatabaseUnavailableException()
        }
    }

    override fun deleteById(id: UUID) {
        try {
            jpaManagerRepository.deleteById(id)
        } catch (ex: Exception) {
            logger.error("$LOG_HEADER -- Error deleting manager by id: $id. Error: $ex")
            throw DatabaseUnavailableException()
        }
    }
}