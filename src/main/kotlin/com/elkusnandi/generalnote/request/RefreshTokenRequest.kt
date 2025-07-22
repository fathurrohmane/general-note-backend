package com.elkusnandi.generalnote.request

import com.fasterxml.jackson.annotation.JsonIgnore

data class RefreshTokenRequest(
    val refreshToken: String
) {
    @JsonIgnore
    var userAgent: String = ""
}