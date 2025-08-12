package com.elkusnandi.playground.controller

import com.elkusnandi.common.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("/test")
class TestController(
    val userService: UserService
) {

    @GetMapping
    fun getHelloWorld(): String {
        return "hello world ${userService.getAllUsers()}"
    }

}