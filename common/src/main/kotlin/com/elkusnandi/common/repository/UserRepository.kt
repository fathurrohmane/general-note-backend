package com.elkusnandi.common.repository

import com.elkusnandi.common.entity.Users
import jakarta.validation.constraints.NotBlank
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<Users, Long> {

    fun findByUserName(userName: String?): Users?

    fun existsByUserName(@NotBlank userName: String): Boolean

}