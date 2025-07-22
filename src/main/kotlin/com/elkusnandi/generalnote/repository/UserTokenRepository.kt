package com.elkusnandi.generalnote.repository

import com.elkusnandi.generalnote.entity.UserToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserTokenRepository : JpaRepository<UserToken, UUID> {

    fun findByUserId(userId: Long): UserToken?

}