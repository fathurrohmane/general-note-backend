package com.elkusnandi.generalnote.controller

import com.elkusnandi.generalnote.common.base.BaseResponse
import com.elkusnandi.generalnote.request.RegisterRequest
import com.elkusnandi.generalnote.response.LoginResponse
import com.elkusnandi.generalnote.response.RegisterResponse
import com.elkusnandi.generalnote.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/public/api/auth")
class AuthenticationController(
    private val userService: UserService
) {

    @GetMapping("/login")
    fun login(@Valid @RequestBody loginRequest: RegisterRequest): BaseResponse<LoginResponse> {
        return BaseResponse(userService.login(loginRequest), HttpStatus.OK)
    }

    @PostMapping("/register")
    fun register(@Valid @RequestBody registerRequest: RegisterRequest): BaseResponse<RegisterResponse> {
        return BaseResponse(userService.register(registerRequest), HttpStatus.CREATED)
    }

}