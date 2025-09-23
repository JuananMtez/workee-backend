package com.workee.api.infrastructure.rest.controller.manager.message.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class CreateManagerRequest(

    @field:NotBlank(message = "Tax identification number cannot be blank")
    @field:JsonProperty(value = "tax_identification_number", required = true)
    val taxIdentificationNumber: String,
    
    @field:JsonProperty(value = "billing_address", required = false)
    val billingAddress: String?


)