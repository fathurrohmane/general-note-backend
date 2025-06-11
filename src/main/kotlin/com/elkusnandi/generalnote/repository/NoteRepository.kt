package com.elkusnandi.generalnote.repository

import com.elkusnandi.generalnote.entity.Notes
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NoteRepository : JpaRepository<Notes, Long> {

    fun findByOwnerId(userId: Long): List<Notes>

}