package com.workee.api.infrastructure.rest.exception.handler

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.workee.api.domain.exception.ErrorCode
import com.workee.api.infrastructure.rest.exception.message.mapper.IExceptionRestMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class AuthEntryPointHandler(private val mapper: IExceptionRestMapper) : AuthenticationEntryPoint {

    override fun commence(req: HttpServletRequest, resp: HttpServletResponse, ex: AuthenticationException) {
        resp.status = HttpServletResponse.SC_UNAUTHORIZED
        resp.contentType = "application/json"
        val body = mapper.asErrorResponse(ErrorCode.INVALID_TOKEN, "Token invalid")
        resp.writer.write(jacksonObjectMapper().writeValueAsString(body))
    }

}