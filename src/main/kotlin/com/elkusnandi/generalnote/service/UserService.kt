package com.elkusnandi.generalnote.service

import com.elkusnandi.generalnote.request.RegisterRequest
import com.elkusnandi.generalnote.response.LoginResponse
import com.elkusnandi.generalnote.response.RegisterResponse
import com.elkusnandi.generalnote.response.UserResponse
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : UserDetailsService {

    fun register(registerRequest: RegisterRequest): RegisterResponse

    fun login(loginRequest: RegisterRequest): LoginResponse

    fun getAllUsers(): List<UserResponse>

    fun addRoleToUser(userId: Long, userRoles: List<Long>): UserResponse

}