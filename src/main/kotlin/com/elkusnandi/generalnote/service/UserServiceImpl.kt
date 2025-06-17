package com.elkusnandi.generalnote.service

import com.elkusnandi.generalnote.entity.Users
import com.elkusnandi.generalnote.repository.UserRepository
import com.elkusnandi.generalnote.request.RegisterRequest
import com.elkusnandi.generalnote.response.LoginResponse
import com.elkusnandi.generalnote.response.RegisterResponse
import com.elkusnandi.generalnote.util.JwtUtil
import org.apache.coyote.BadRequestException
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
            throw BadRequestException("username already registered")
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

    override fun login(loginRequest: RegisterRequest): LoginResponse {
        // Check if user exists
        val currentUser = userRepository.findByUserName(loginRequest.userName.lowercase())
            ?: throw BadRequestException("Username or password not match")

        // Check user password
        if (bcrypt.matches(loginRequest.password, currentUser.password)) {
            val token = JwtUtil.generateToken(currentUser.id.toString())
            return LoginResponse(token = token)
        } else {
            throw BadRequestException("Username or password not match")
        }
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