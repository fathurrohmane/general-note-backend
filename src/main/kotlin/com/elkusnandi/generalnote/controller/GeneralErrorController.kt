package com.elkusnandi.generalnote.controller

import com.elkusnandi.generalnote.common.base.BaseResponse
import jakarta.servlet.RequestDispatcher
import jakarta.servlet.http.HttpServletRequest
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/error")
class GeneralErrorController : ErrorController {
    @RequestMapping
    fun handleError(request: HttpServletRequest): BaseResponse<Void> {
        val status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)
        val statusCode = status?.toString()?.toInt() ?: 500

        return BaseResponse(data = null, status = HttpStatus.valueOf(statusCode), success = false, "Error")
    }
}
