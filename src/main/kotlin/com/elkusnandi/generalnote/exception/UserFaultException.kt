package com.elkusnandi.generalnote.exception

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

class UserFaultException(
    val httpCode: HttpStatusCode = HttpStatus.BAD_REQUEST,
    message: String
) : RuntimeException(message)