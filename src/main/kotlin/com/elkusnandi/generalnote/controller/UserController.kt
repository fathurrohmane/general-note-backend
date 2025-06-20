package com.elkusnandi.generalnote.controller

import com.elkusnandi.generalnote.common.base.BaseResponse
import com.elkusnandi.generalnote.response.UserResponse
import com.elkusnandi.generalnote.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {

    @PreAuthorize("hasRole('admin')")
    @GetMapping
    fun getAllUsers(): BaseResponse<List<UserResponse>> {
        return BaseResponse(data = userService.getAllUsers(), HttpStatus.OK)
    }
}