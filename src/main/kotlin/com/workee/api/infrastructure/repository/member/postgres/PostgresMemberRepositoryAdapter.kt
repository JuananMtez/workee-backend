package com.workee.api.infrastructure.repository.member.postgres

import com.workee.api.domain.exception.DatabaseUnavailableException
import com.workee.api.domain.exception.UserNotFoundException
import com.workee.api.domain.model.member.Member
import com.workee.api.domain.repository.member.MemberRepository
import com.workee.api.infrastructure.repository.member.JpaMemberRepository
import com.workee.api.infrastructure.repository.member.MemberRepositoryMapper

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class PostgresMemberRepositoryAdapter(
    private val jpaMemberRepository: JpaMemberRepository,
    private val memberRepositoryMapper: MemberRepositoryMapper
) : MemberRepository {

    companion object {
        private const val LOG_HEADER = "[POSTGRES MEMBER REPOSITORY]"
        private val logger = LoggerFactory.getLogger(MemberRepository::class.java)
    }

    override fun findById(id: UUID): Member {
        try {
            val member = jpaMemberRepository.findById(id).orElseThrow { UserNotFoundException() }
            return memberRepositoryMapper.asMember(member)
        } catch (ex: Exception) {
            logger.error("$LOG_HEADER -- Error finding member by id with id: $id. Error: $ex")
            throw DatabaseUnavailableException()
        }
    }

    override fun existsByUsernameOrEmail(email: String, username: String): Boolean {
        try {
            return jpaMemberRepository.existsByUserEmail(email) || jpaMemberRepository.existsByUserUsername(username)
        } catch (ex: Exception) {
            logger.error("$LOG_HEADER -- Error checking if exists by email: $email or username: $username. Error: $ex")
            throw DatabaseUnavailableException()
        }
    }

    override fun save(member: Member): Member {
        try {
            val memberEntity = memberRepositoryMapper.asCreateMemberEntity(member)
            return memberRepositoryMapper.asMember(jpaMemberRepository.save(memberEntity))
        } catch (ex: Exception) {
            logger.error("$LOG_HEADER -- Error saving member: $member. Error: $ex")
            throw DatabaseUnavailableException()
        }
    }

    override fun update(member: Member): Member {
        try {
            val memberEntity = memberRepositoryMapper.asMemberEntity(member)
            return memberRepositoryMapper.asMember(jpaMemberRepository.save(memberEntity))
        } catch (ex: Exception) {
            logger.error("$LOG_HEADER -- Error updating member: $member. Error: $ex")
            throw DatabaseUnavailableException()
        }
    }

    override fun deleteById(id: UUID) {
        try {
            jpaMemberRepository.deleteById(id)
        } catch (ex: Exception) {
            logger.error("$LOG_HEADER -- Error deleting member by id: $id. Error: $ex")
            throw DatabaseUnavailableException()
        }
    }
}