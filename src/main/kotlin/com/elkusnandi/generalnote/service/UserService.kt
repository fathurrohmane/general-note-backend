package com.elkusnandi.generalnote.service

import com.elkusnandi.generalnote.entity.Users
import com.elkusnandi.generalnote.request.RegisterRequest

interface UserService {

    fun register(registerRequest: RegisterRequest): Users

    fun login(loginRequest: RegisterRequest): Users?

}