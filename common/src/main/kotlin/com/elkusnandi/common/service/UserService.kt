package com.elkusnandi.common.service

import com.elkusnandi.common.request.RefreshTokenRequest
import com.elkusnandi.common.request.RegisterRequest
import com.elkusnandi.common.response.LoginResponse
import com.elkusnandi.common.response.RegisterResponse
import com.elkusnandi.common.response.UserResponse
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : UserDetailsService {

    fun register(registerRequest: RegisterRequest): RegisterResponse

    fun login(loginRequest: RegisterRequest): LoginResponse

    fun refreshToken(refreshTokenRequest: RefreshTokenRequest): LoginResponse

    fun getAllUsers(): List<UserResponse>

    fun addRoleToUser(userId: Long, userRoles: List<Long>): UserResponse

}