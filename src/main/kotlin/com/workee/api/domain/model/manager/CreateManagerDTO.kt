package com.workee.api.domain.model.manager

data class CreateManagerDTO(

    val taxIdentificationNumber: String,
    val billingAddress: String?

)