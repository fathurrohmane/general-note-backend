package com.elkusnandi.generalnote.service

import com.elkusnandi.generalnote.entity.Users
import com.elkusnandi.generalnote.repository.UserRepository
import com.elkusnandi.generalnote.request.RegisterRequest
import com.elkusnandi.generalnote.response.RegisterResponse
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val bcrypt: BCryptPasswordEncoder
) : UserService {

    override fun register(registerRequest: RegisterRequest): RegisterResponse {
        // check for duplicate username
        if (userRepository.existsByUserName(registerRequest.userName.lowercase())) {
            throw RuntimeException("username already registered")
        }

        val newUser = userRepository.save(
            Users(
                userName = registerRequest.userName.trim().lowercase(),
                password = bcrypt.encode(registerRequest.password)
            )
        )

        return RegisterResponse(
            id = newUser.id,
            userName = newUser.userName
        )
    }

    override fun login(loginRequest: RegisterRequest): Users? {
        return userRepository.findByUserName(loginRequest.userName)
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val currentUser = userRepository.findById(username?.toLong() ?: -1)

        if (currentUser.isEmpty) {
            throw NullPointerException()
        } else {
            return User.builder()
                .username(currentUser.get().id.toString())
                .password(currentUser.get().password)
                .roles("user")
                .build()
        }
    }

}