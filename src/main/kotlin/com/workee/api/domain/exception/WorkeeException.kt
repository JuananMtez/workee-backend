package com.workee.api.domain.exception

import java.time.LocalDateTime

sealed class WorkeeException (
    val code: ErrorCode,
    override val message: String,
) : RuntimeException(message)


class InvalidTokenException : WorkeeException(ErrorCode.INVALID_TOKEN, "Token is invalid")