package com.elkusnandi.generalnote.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterRequest(
    @field:NotBlank
    @field:Size(min = 4, max = 40)
    val userName: String,
    @field:Size(min = 6, max = 40)
    val password: String
)