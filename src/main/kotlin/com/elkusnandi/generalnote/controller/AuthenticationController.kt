package com.elkusnandi.generalnote.controller

import com.elkusnandi.generalnote.request.RegisterRequest
import com.elkusnandi.generalnote.service.UserService
import com.elkusnandi.generalnote.util.JwtUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("api/auth")
class AuthenticationController(
    private val userService: UserService,
    private val bcrypt: BCryptPasswordEncoder
) {

    @GetMapping("/login")
    fun login(@RequestBody loginRequest: RegisterRequest): ResponseEntity<String> {
        val currentUser = userService.login(loginRequest)

        return if (currentUser != null && bcrypt.matches(loginRequest.password, currentUser.password)) {
            val token = JwtUtil.generateToken(currentUser.userName)
            ResponseEntity(token, HttpStatus.OK)
        } else {
            ResponseEntity("Username or password not match", HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegisterRequest): String {
        return userService.register(registerRequest).id.toString()
    }

}