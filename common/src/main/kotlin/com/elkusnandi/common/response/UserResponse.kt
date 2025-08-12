package com.elkusnandi.common.response

class UserResponse(
    val id: Long,
    val userName: String,
    val password: String,
    val roles: List<RoleResponse>
)