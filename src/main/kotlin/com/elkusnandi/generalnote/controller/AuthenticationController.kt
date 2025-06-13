package com.elkusnandi.generalnote.controller

import com.elkusnandi.generalnote.common.base.BaseResponse
import com.elkusnandi.generalnote.request.RegisterRequest
import com.elkusnandi.generalnote.service.UserService
import com.elkusnandi.generalnote.util.JwtUtil
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/public/api/auth")
class AuthenticationController(
    private val userService: UserService,
    private val bcrypt: BCryptPasswordEncoder
) {

    @GetMapping("/login")
    fun login(@RequestBody loginRequest: RegisterRequest): BaseResponse<String> {
        val currentUser = userService.login(loginRequest)

        return if (currentUser != null && bcrypt.matches(loginRequest.password, currentUser.password)) {
            val token = JwtUtil.generateToken(currentUser.id.toString())
            BaseResponse(token, HttpStatus.OK)
        } else {
            BaseResponse(message = "Username or password not match", status = HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegisterRequest): BaseResponse<Boolean> {
        return BaseResponse(userService.register(registerRequest).id > -1, HttpStatus.OK)
    }

}