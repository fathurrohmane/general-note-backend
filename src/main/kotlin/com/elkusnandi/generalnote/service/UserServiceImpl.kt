package com.elkusnandi.generalnote.service

import com.elkusnandi.generalnote.entity.Users
import com.elkusnandi.generalnote.repository.UserRepository
import com.elkusnandi.generalnote.request.RegisterRequest
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val bcrypt: BCryptPasswordEncoder
) : UserService {
    override fun register(registerRequest: RegisterRequest): Users {
        return userRepository.save(
            Users(
                userName = registerRequest.userName,
                password = bcrypt.encode(registerRequest.password)
            )
        )
    }

    override fun login(loginRequest: RegisterRequest): Users? {
        return userRepository.findByUserName(loginRequest.userName)
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val currentUser = userRepository.findByUserName(username)

        if (currentUser == null) {
            throw NullPointerException()
        } else {
            return User.builder()
                .username(currentUser.userName)
                .password(currentUser.password)
                .roles("user")
                .build()
        }
    }

}