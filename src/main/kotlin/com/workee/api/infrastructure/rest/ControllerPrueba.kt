package com.workee.api.infrastructure.rest

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ControllerPrueba {

    @GetMapping("/hola-cliente")
    @PreAuthorize("hasRole('user_client_role')")
    fun holaCliente(): String = "Hola Cliente seguro con Keycloak JWT"

    @GetMapping("/hola-empresario")
    @PreAuthorize("hasRole('EMPRESARIO')")
    fun holaEmpresario(): String = "Hola Empresario seguro con Keycloak JWT"
}