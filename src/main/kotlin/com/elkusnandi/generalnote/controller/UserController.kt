package com.elkusnandi.generalnote.controller

import com.elkusnandi.generalnote.common.base.BaseResponse
import com.elkusnandi.generalnote.response.UserResponse
import com.elkusnandi.generalnote.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
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

    @Operation(
        summary = "Get all registered user",
        description = "Get all registered user only for admin to access"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "List of users"),
            ApiResponse(responseCode = "403", description = "Access denied")
        ]
    )
    @PreAuthorize("hasRole('admin')")
    @GetMapping
    fun getAllUsers(): BaseResponse<List<UserResponse>> {
        return BaseResponse(data = userService.getAllUsers(), HttpStatus.OK)
    }
}