package com.elkusnandi.generalnote.advice

import com.elkusnandi.generalnote.common.base.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException


@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNotFound(ex: NoHandlerFoundException): ResponseEntity<*> {
        return BaseResponse(
            data = null,
            status = HttpStatus.NOT_FOUND,
            success = false,
            ex.message.toString()
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleGlobalException(ex: Exception?): ResponseEntity<*> {
        return BaseResponse(
            data = null,
            status = HttpStatus.INTERNAL_SERVER_ERROR,
            success = false,
            ex?.message.toString()
        )
    }
}
