package com.elkusnandi.common.response

data class LoginResponse(
    val token: String,
    val refreshToken: String,
)