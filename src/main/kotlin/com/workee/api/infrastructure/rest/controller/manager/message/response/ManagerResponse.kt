package com.workee.api.infrastructure.rest.controller.manager.message.response

import com.fasterxml.jackson.annotation.JsonProperty

data class ManagerResponse (

    val username: String,

    val name: String,

    @field:JsonProperty(value = "first_surname")
    val firstSurname: String,

    @field:JsonProperty(value = "second_surname")
    val secondSurname: String?,

    val email: String,

    @field:JsonProperty(value = "tax_identification_number")
    val taxIdentificationNumber: String,

    @field:JsonProperty(value = "billing_address")
    val billingAddress: String?

)