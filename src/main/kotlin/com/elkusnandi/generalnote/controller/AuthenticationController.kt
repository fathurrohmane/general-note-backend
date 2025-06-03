package com.elkusnandi.generalnote.controller

import com.elkusnandi.generalnote.request.RegisterRequest
import com.elkusnandi.generalnote.service.UserService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("api/auth")
class AuthenticationController(
    private val userService: UserService
) {

    @GetMapping("/login")
    fun login(@RequestBody loginRequest: RegisterRequest): String {
        return userService.login(loginRequest)?.userName ?: "Not found"
    }

    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegisterRequest): String {
        return userService.register(registerRequest).id.toString()
    }

}