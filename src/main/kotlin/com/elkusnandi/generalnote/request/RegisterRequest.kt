package com.elkusnandi.generalnote.request

import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterRequest(
    @field:NotBlank
    @field:Size(min = 4, max = 40)
    @Schema(
        description = "Username",
        example = "admin",
        minLength = 4,
        maxLength = 40
    )
    val userName: String,
    @field:Size(min = 6, max = 40)
    @Schema(
        description = "Password (no regex)",
        example = "123456",
        minLength = 6,
        maxLength = 40
    )
    val password: String
) {
    @JsonIgnore
    var userAgent: String = ""
}