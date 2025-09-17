package com.workee.api.infrastructure.rest.controller.exception.message.mapper

import com.workee.api.domain.exception.ErrorCode
import com.workee.api.domain.exception.WorkeeException
import com.workee.api.infrastructure.rest.controller.exception.message.ErrorResponse
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.ReportingPolicy
import java.time.LocalDateTime

@Mapper(
    componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, imports = [LocalDateTime::class]
)
interface ExceptionRestMapper {

    @Mapping(target = "code", expression = "java(ex.getCode().toString())")
    @Mapping(target = "timestamp", expression = "java(LocalDateTime.now().toString())")
    fun asErrorResponse(ex: WorkeeException): ErrorResponse

    @Mapping(target = "code", expression = "java(errorCode.toString())")
    @Mapping(target = "timestamp", expression = "java(LocalDateTime.now().toString())")
    fun asErrorResponse(errorCode: ErrorCode, message: String): ErrorResponse

}