package com.workee.api.infrastructure.rest.controller.config.springdoc

import org.springdoc.core.customizers.OperationCustomizer
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import io.swagger.v3.oas.models.Operation

@Component
class MethodNameOperationCustomizer : OperationCustomizer {
    override fun customize(operation: Operation, handlerMethod: HandlerMethod): Operation {
        if (operation.summary.isNullOrBlank()) {
            val methodName = handlerMethod.method.name
            // Convierte "createUser" -> "Create User"
            val summary = methodName.replace(Regex("([a-z])([A-Z])"), "$1 $2")
                .replaceFirstChar { it.uppercase() }
            operation.summary = summary
        }
        return operation
    }
}