package com.workee.api.domain.exception

sealed class WorkeeException (
    val code: ErrorCode,
    override val message: String,
) : RuntimeException(message)


class InvalidTokenException : WorkeeException(ErrorCode.INVALID_TOKEN, "Token is invalid")
class ResourceNotAccessibleException : WorkeeException(ErrorCode.RESOURCE_NOT_ACCESSIBLE, "Resource not accessible")
class UnauthorizedUserException : WorkeeException(ErrorCode.UNAUTHORIZED_USER, "Unauthorized user")
class UserNotFoundException : WorkeeException(ErrorCode.USER_NOT_FOUND, "User not found")
class DatabaseUnavailableException : WorkeeException(ErrorCode.DATABASE_UNAVAILABLE, "Database unavailable")
class KeycloakErrorException : WorkeeException(ErrorCode.KEYCLOAK_ERROR, "Keycloak error")
class UsernameOrEmailAlreadyExistsException : WorkeeException(ErrorCode.USERNAME_OR_EMAIL_ALREADY_EXISTS, "Username or email already exists")

