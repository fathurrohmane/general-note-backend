package com.elkusnandi.common.service

import com.elkusnandi.common.entity.Role
import com.elkusnandi.common.entity.UserToken
import com.elkusnandi.common.entity.Users
import com.elkusnandi.common.exception.UserFaultException
import com.elkusnandi.common.repository.RoleRepository
import com.elkusnandi.common.repository.UserRepository
import com.elkusnandi.common.repository.UserTokenRepository
import com.elkusnandi.common.request.RefreshTokenRequest
import com.elkusnandi.common.request.RegisterRequest
import com.elkusnandi.common.response.LoginResponse
import com.elkusnandi.common.response.RegisterResponse
import com.elkusnandi.common.response.RoleResponse
import com.elkusnandi.common.response.UserResponse
import com.elkusnandi.common.util.JwtUtil
import org.apache.coyote.BadRequestException
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder,
    private val userTokenRepository: UserTokenRepository
) : UserService {

    override fun register(registerRequest: RegisterRequest): RegisterResponse {
        // check for duplicate username
        if (userRepository.existsByUserName(registerRequest.userName.trim().lowercase())) {
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
                password = passwordEncoder.encode(registerRequest.password),
            ).apply {
                this.roles = roles
            }
        )

        return RegisterResponse(
            id = newUser.id,
            userName = newUser.userName
        )
    }

    @Transactional
    override fun login(loginRequest: RegisterRequest): LoginResponse {
        // Check if user exists
        val currentUser = userRepository.findByUserName(loginRequest.userName.lowercase())
            ?: throw BadRequestException("Username or password not match")

        // Check user password
        if (passwordEncoder.matches(loginRequest.password, currentUser.password)) {
            val token = JwtUtil.generateToken(currentUser.id.toString(), UUID.randomUUID().toString())
            val refreshTokenId = UUID.randomUUID().toString()
            val refreshToken = JwtUtil.generateToken(currentUser.id.toString(), refreshTokenId, true)

            userTokenRepository.save(
                UserToken(
                    tokenId = refreshTokenId,
                    userAgent = loginRequest.userAgent
                ).apply {
                    user = currentUser
                }
            )

            return LoginResponse(token = token, refreshToken = refreshToken)
        } else {
            throw BadRequestException("Username or password not match")
        }
    }

    override fun refreshToken(refreshTokenRequest: RefreshTokenRequest): LoginResponse {
        val claims = JwtUtil.checkAndGetClaims(refreshTokenRequest.refreshToken)
            ?: throw UserFaultException(message = "Refresh token not valid")

        val userName = claims.subject ?: throw UserFaultException(message = "Claim subject not found in the token")

        val currentUser = userRepository.findById(userName.toLong())
            .orElseThrow { throw UserFaultException(message = "User not found or subject not found in the token") }
        val userToken = userTokenRepository.findByUserId(currentUser.id)

        return if (userToken != null && userToken.tokenId == claims.id) {
            val token = JwtUtil.generateToken(currentUser.id.toString(), UUID.randomUUID().toString())
            val refreshTokenId = UUID.randomUUID().toString()
            val refreshToken = JwtUtil.generateToken(currentUser.id.toString(), refreshTokenId, true)

            userTokenRepository.save(
                UserToken(
                    id = userToken.id,
                    tokenId = refreshTokenId,
                    userAgent = refreshTokenRequest.userAgent
                ).apply {
                    user = currentUser
                }
            )

            LoginResponse(token = token, refreshToken = refreshToken)
        } else {
            throw UserFaultException(message = "Refresh token not found or match for this user")
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