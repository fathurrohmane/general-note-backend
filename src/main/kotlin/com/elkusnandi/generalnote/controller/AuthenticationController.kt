package com.elkusnandi.generalnote.controller

import com.elkusnandi.generalnote.common.base.BaseResponse
import com.elkusnandi.generalnote.request.RefreshTokenRequest
import com.elkusnandi.generalnote.request.RegisterRequest
import com.elkusnandi.generalnote.response.LoginResponse
import com.elkusnandi.generalnote.response.RegisterResponse
import com.elkusnandi.generalnote.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

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
    fun login(
        @Valid @RequestBody loginRequest: RegisterRequest,
        @RequestHeader(value = HttpHeaders.USER_AGENT) userAgent: String
    ): BaseResponse<LoginResponse> {
        loginRequest.userAgent = userAgent
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

    @Operation(
        summary = "Refresh user token using refresh token",
        description = "Return newly user token and refresh token using refresh token"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "user token and refresh token"),
            ApiResponse(responseCode = "400", description = "Refresh token not valid"),
            ApiResponse(responseCode = "400", description = "Claim subject not found in the token"),
            ApiResponse(responseCode = "400", description = "User not found or subject not found in the token"),
            ApiResponse(responseCode = "400", description = "Refresh token not found or match for this user"),
        ]
    )
    @PostMapping("/refresh-token")
    fun refreshToken(
        @Valid @RequestBody refreshTokenRequest: RefreshTokenRequest,
        @RequestHeader(value = HttpHeaders.USER_AGENT) userAgent: String
    ): BaseResponse<LoginResponse> {
        refreshTokenRequest.userAgent = userAgent
        return BaseResponse(userService.refreshToken(refreshTokenRequest), HttpStatus.CREATED)
    }

}