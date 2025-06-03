package com.elkusnandi.generalnote.service

import com.elkusnandi.generalnote.entity.Users
import com.elkusnandi.generalnote.repository.UserRepository
import com.elkusnandi.generalnote.request.RegisterRequest
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {
    override fun register(registerRequest: RegisterRequest): Users {
        return userRepository.save(
            Users(
                userName = registerRequest.userName,
                password = registerRequest.password
            )
        )
    }

    override fun login(loginRequest: RegisterRequest): Users? {
        return userRepository.findByUserName(loginRequest.userName)
    }

}