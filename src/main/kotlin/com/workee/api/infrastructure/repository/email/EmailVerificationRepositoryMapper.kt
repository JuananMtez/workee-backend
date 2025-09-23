package com.workee.api.infrastructure.repository.email

import com.workee.api.domain.model.email.EmailVerification
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface EmailVerificationRepositoryMapper {

    fun asEmailVerification(emailVerificationEntity: EmailVerificationEntity): EmailVerification
    fun asEmailVerificationEntity(emailVerification: EmailVerification): EmailVerificationEntity

}