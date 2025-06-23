package com.elkusnandi.generalnote.util

import com.elkusnandi.generalnote.entity.Role
import com.elkusnandi.generalnote.repository.RoleRepository
import com.elkusnandi.generalnote.repository.UserRepository
import com.elkusnandi.generalnote.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

//@Component
class InitAdmin : CommandLineRunner {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var roleRepository: RoleRepository

    @Autowired
    private lateinit var userService: UserService

    @Transactional
    override fun run(vararg args: String?) {
        val adminRole = roleRepository.save(
            Role(
                name = "admin"
            )
        )
        val userRole = roleRepository.save(
            Role(
                name = "user"
            )
        )
        val users = userRepository.findAll()
        users.forEach {
            val roleIds = if (it.userName.contains("admin")) {
                listOf(adminRole.id, userRole.id)
            } else {
                listOf(userRole.id)
            }
            userService.addRoleToUser(it.id, roleIds)
        }
    }
}