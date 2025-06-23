package com.elkusnandi.generalnote.exception

import com.elkusnandi.generalnote.common.base.BaseResponse
import org.apache.coyote.BadRequestException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authorization.AuthorizationDeniedException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException
import java.util.function.Consumer


@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException::class)
    @ResponseBody
    fun handleNotFound(ex: NoHandlerFoundException): ResponseEntity<*> {
        return BaseResponse(
            data = null,
            status = HttpStatus.NOT_FOUND,
            success = false,
            ex.message.toString()
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseBody
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<*> {
        val errors: MutableMap<String, String?> = HashMap()
        ex.bindingResult.fieldErrors.forEach(Consumer { error: FieldError ->
            errors[error.field] = error.defaultMessage
        })
        return BaseResponse(
            data = null,
            status = HttpStatus.BAD_REQUEST,
            success = false,
            message = errors.toString()
        )
    }

    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun handleGlobalException(ex: Exception?): ResponseEntity<*> {
        return BaseResponse(
            data = null,
            status = HttpStatus.INTERNAL_SERVER_ERROR,
            success = false,
            ex?.message.toString()
        )
    }

    @ExceptionHandler(AuthorizationDeniedException::class)
    @ResponseBody
    fun handleGlobalException(ex: AuthorizationDeniedException?): ResponseEntity<*> {
        return BaseResponse(
            data = null,
            status = HttpStatus.FORBIDDEN,
            success = false,
            ex?.message.toString()
        )
    }

    @ExceptionHandler(UserFaultException::class)
    @ResponseBody
    fun handleGlobalException(ex: UserFaultException?): ResponseEntity<*> {
        return BaseResponse(
            data = null,
            status = ex?.httpCode ?: HttpStatus.INTERNAL_SERVER_ERROR,
            success = false,
            ex?.message.toString()
        )
    }

    @ExceptionHandler(BadRequestException::class)
    @ResponseBody
    fun resolveException(exception: BadRequestException): ResponseEntity<*> {
        return BaseResponse(
            data = null,
            status = HttpStatus.BAD_REQUEST,
            success = false,
            exception.message.toString()
        )
    }
}
