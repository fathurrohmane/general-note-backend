package com.elkusnandi.playground.repository

import com.elkusnandi.playground.entity.Notes
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NoteRepository : JpaRepository<Notes, Long> {

    fun findByOwnerId(userId: Long): List<Notes>

}