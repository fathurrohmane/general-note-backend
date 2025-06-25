package com.elkusnandi.generalnote.service

import com.elkusnandi.generalnote.entity.Role
import com.elkusnandi.generalnote.entity.Users
import com.elkusnandi.generalnote.repository.RoleRepository
import com.elkusnandi.generalnote.repository.UserRepository
import com.elkusnandi.generalnote.request.RegisterRequest
import com.elkusnandi.generalnote.response.LoginResponse
import com.elkusnandi.generalnote.response.RegisterResponse
import com.elkusnandi.generalnote.response.RoleResponse
import com.elkusnandi.generalnote.response.UserResponse
import com.elkusnandi.generalnote.util.JwtUtil
import org.apache.coyote.BadRequestException
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val bcrypt: BCryptPasswordEncoder
) : UserService {

    override fun register(registerRequest: RegisterRequest): RegisterResponse {
        // check for duplicate username
        if (userRepository.existsByUserName(registerRequest.userName.lowercase())) {
            throw BadRequestException("username already registered")
        }

        val adminRole = roleRepository.findByName("admin")
        val userRole = roleRepository.findByName("user") ?: throw IllegalStateException("Missing user role data")
        val roles = mutableSetOf<Role>()

        if (registerRequest.userName == "admin" && adminRole != null) {
            roles.add(adminRole)
        }
        roles.add(userRole)

        val newUser = userRepository.save(
            Users(
                userName = registerRequest.userName.trim().lowercase(),
                password = bcrypt.encode(registerRequest.password),
            ).apply {
                this.roles = roles
            }
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

    @PreAuthorize("hasRole('admin')")
    override fun getAllUsers(): List<UserResponse> {
        return userRepository.findAll().map {
            UserResponse(it.id, it.userName, it.password, it.roles.map { role -> RoleResponse(role.id, role.name) })
        }
    }

    override fun addRoleToUser(userId: Long, userRoles: List<Long>): UserResponse {
        val currentUser = userRepository.findById(userId)

        val roles = roleRepository.findAllById(userRoles)

        val newUser = userRepository.save(
            currentUser.get().apply {
                this.roles = roles.toMutableSet()
            }
        )

        return UserResponse(
            id = newUser.id,
            userName = newUser.userName,
            password = newUser.password,
            roles = newUser.roles.map {
                RoleResponse(
                    it.id,
                    it.name
                )
            }
        )
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val currentUser = userRepository.findById(username?.toLong() ?: -1)

        if (currentUser.isEmpty) {
            throw NullPointerException()
        } else {
            return User.builder()
                .username(currentUser.get().id.toString())
                .password(currentUser.get().password)
                .roles(*currentUser.get().roles.map { it.name }.toTypedArray())
                .build()
        }
    }

}