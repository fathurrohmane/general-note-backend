package com.elkusnandi.generalnote.controller

import com.elkusnandi.generalnote.common.base.BaseResponse
import com.elkusnandi.generalnote.request.RegisterRequest
import com.elkusnandi.generalnote.response.LoginResponse
import com.elkusnandi.generalnote.response.RegisterResponse
import com.elkusnandi.generalnote.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/public/api/auth")
class AuthenticationController(
    private val userService: UserService
) {

    @Operation(
        summary = "Login with username and password",
        description = "Get user token by logging in with username and password"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "User access token"),
            ApiResponse(responseCode = "400", description = "Bad request")
        ]
    )
    @PostMapping("/login")
    fun login(@Valid @RequestBody loginRequest: RegisterRequest): BaseResponse<LoginResponse> {
        return BaseResponse(userService.login(loginRequest), HttpStatus.OK)
    }

    @Operation(
        summary = "Register new account to api",
        description = "Return newly registered user with userId. Please continue to /login."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "UserId and username"),
            ApiResponse(responseCode = "400", description = "Username already registered")
        ]
    )
    @PostMapping("/register")
    fun register(@Valid @RequestBody registerRequest: RegisterRequest): BaseResponse<RegisterResponse> {
        return BaseResponse(userService.register(registerRequest), HttpStatus.CREATED)
    }

}