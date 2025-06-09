package com.elkusnandi.generalnote.service

import com.elkusnandi.generalnote.entity.Users
import com.elkusnandi.generalnote.request.RegisterRequest
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : UserDetailsService {

    fun register(registerRequest: RegisterRequest): Users

    fun login(loginRequest: RegisterRequest): Users?

}