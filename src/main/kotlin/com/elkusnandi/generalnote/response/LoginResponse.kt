package com.elkusnandi.generalnote.response

data class LoginResponse(
    val token: String,
    val refreshToken: String,
)