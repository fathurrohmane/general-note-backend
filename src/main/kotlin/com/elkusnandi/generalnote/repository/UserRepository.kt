package com.elkusnandi.generalnote.repository

import com.elkusnandi.generalnote.entity.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<Users, Long> {

    fun findByUserName(userName: String): Users?

}