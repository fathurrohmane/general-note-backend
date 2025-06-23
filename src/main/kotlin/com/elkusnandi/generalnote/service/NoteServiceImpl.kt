package com.elkusnandi.generalnote.service

import com.elkusnandi.generalnote.entity.Notes
import com.elkusnandi.generalnote.exception.UserFaultException
import com.elkusnandi.generalnote.repository.NoteRepository
import com.elkusnandi.generalnote.repository.UserRepository
import com.elkusnandi.generalnote.response.NotesResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class NoteServiceImpl(
    private val noteRepository: NoteRepository,
    private val userRepository: UserRepository
) : NoteService {
    override fun getNotes(ownerId: Long): List<NotesResponse> {
        return noteRepository.findByOwnerId(ownerId).map {
            NotesResponse(
                it.id,
                it.title,
                it.content
            )
        }
    }

    override fun getNote(ownerId: Long, noteId: Long): NotesResponse {
        return noteRepository.findById(noteId).map {
            NotesResponse(
                it.id,
                it.title,
                it.content
            )
        }.orElseThrow {
            UserFaultException(HttpStatus.NOT_FOUND, "Not found")
        }
    }

    override fun upsertNote(note: Notes): NotesResponse {
        // Need to get user if not it will cause error
        val currentUser = userRepository.findById(note.owner?.id ?: -1).get()
        return noteRepository.save(
            Notes(
                id = note.id,
                title = note.title,
                content = note.content,
                owner = currentUser
            )
        ).let {
            NotesResponse(
                it.id,
                it.title,
                it.content
            )
        }
    }

    override fun deleteNote(noteId: Long) {
        return noteRepository.deleteById(noteId)
    }
}