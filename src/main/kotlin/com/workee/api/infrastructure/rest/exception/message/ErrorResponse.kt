package com.workee.api.infrastructure.rest.exception.message

import java.time.LocalDateTime

class ErrorResponse(
    val code: String,
    val message: String,
    val timestamp: String
)