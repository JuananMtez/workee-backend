package com.workee.api.infrastructure.rest.controller.manager.message.response

data class ManagerResponse (

    val username: String,

    val name: String,

    val firstSurname: String,

    val secondSurname: String?,

    val email: String,

    val taxIdentificationNumber: String,

    val billingAddress: String?

)