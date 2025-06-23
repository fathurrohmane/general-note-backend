package com.elkusnandi.generalnote.repository

import com.elkusnandi.generalnote.entity.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : JpaRepository<Role, Long> {
}