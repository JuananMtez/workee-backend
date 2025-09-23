package com.workee.api.infrastructure.rest.controller.exception.handler

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.workee.api.domain.exception.InvalidTokenException
import com.workee.api.infrastructure.rest.controller.exception.message.mapper.ExceptionRestMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class AuthEntryPointHandler(private val mapper: ExceptionRestMapper) : AuthenticationEntryPoint {

    override fun commence(req: HttpServletRequest, resp: HttpServletResponse, ex: AuthenticationException) {
        val ex = InvalidTokenException()
        resp.status = HttpServletResponse.SC_UNAUTHORIZED
        resp.contentType = "application/json"
        val body = mapper.asErrorResponse(ex.code, ex.message)
        resp.writer.write(jacksonObjectMapper().writeValueAsString(body))


    }

}