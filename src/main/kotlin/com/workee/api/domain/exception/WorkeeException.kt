package com.workee.api.domain.exception

sealed class WorkeeException (
    val code: ErrorCode,
    override val message: String,
) : RuntimeException(message)


class InvalidTokenException : WorkeeException(ErrorCode.INVALID_TOKEN, "Token is invalid")
class ResourceNotAccessibleException : WorkeeException(ErrorCode.RESOURCE_NOT_ACCESSIBLE, "Resource not accessible")
class UnauthorizedUserException : WorkeeException(ErrorCode.UNAUTHORIZED_USER, "Unauthorized user")
