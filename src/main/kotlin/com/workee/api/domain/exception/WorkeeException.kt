package com.workee.api.domain.exception

import org.springframework.http.HttpStatus

sealed class WorkeeException(
    val code: ErrorCode,
    override val message: String,
    val status: HttpStatus,
) : RuntimeException(message)

class InvalidTokenException :
    WorkeeException(
        ErrorCode.INVALID_TOKEN,
        "Token is invalid",
        HttpStatus.BAD_REQUEST
    )

class ResourceNotAccessibleException :
    WorkeeException(
        ErrorCode.RESOURCE_NOT_ACCESSIBLE,
        "Resource not accessible",
        HttpStatus.SERVICE_UNAVAILABLE
    )

class UnauthorizedUserException :
    WorkeeException(
        ErrorCode.UNAUTHORIZED_USER,
        "Unauthorized user",
        HttpStatus.UNAUTHORIZED
    )

class UserNotFoundException :
    WorkeeException(
        ErrorCode.USER_NOT_FOUND,
        "User not found",
        HttpStatus.NOT_FOUND
    )

class DatabaseUnavailableException :
    WorkeeException(
        ErrorCode.DATABASE_UNAVAILABLE,
        "Database unavailable",
        HttpStatus.SERVICE_UNAVAILABLE
    )

class KeycloakErrorException :
    WorkeeException(
        ErrorCode.KEYCLOAK_ERROR,
        "Keycloak error",
        HttpStatus.SERVICE_UNAVAILABLE
    )

class UsernameOrEmailAlreadyExistsException :
    WorkeeException(
        ErrorCode.USERNAME_OR_EMAIL_ALREADY_EXISTS,
        "Username or email already exists",
        HttpStatus.CONFLICT
    )

class ManagerAlreadyExistsException :
    WorkeeException(
        ErrorCode.MANAGER_ALREADY_EXISTS,
        "Manager already exists",
        HttpStatus.CONFLICT
    )

class SmtpException :
    WorkeeException(
        ErrorCode.SMTP_EXCEPTION,
        "SMTP exception",
        HttpStatus.SERVICE_UNAVAILABLE
    )

class EmailVerificationNotFoundException :
    WorkeeException(
        ErrorCode.EMAIL_VERIFICATION_NOT_FOUND,
        "SMTP exception",
        HttpStatus.NOT_FOUND
    )

class EmailNotVerifiedException :
    WorkeeException(
        ErrorCode.EMAIL_NOT_VERIFIED,
        "Email not verified",
        HttpStatus.FORBIDDEN
    )

class RefreshTokenNotFoundException :
    WorkeeException(
        ErrorCode.REFRESH_TOKEN_NOT_FOUND,
        "Refresh token not found",
        HttpStatus.UNAUTHORIZED
    )
