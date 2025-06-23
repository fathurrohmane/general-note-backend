package com.elkusnandi.generalnote.service

import com.elkusnandi.generalnote.entity.Notes
import com.elkusnandi.generalnote.exception.UserFaultException
import com.elkusnandi.generalnote.repository.NoteRepository
import com.elkusnandi.generalnote.repository.UserRepository
import com.elkusnandi.generalnote.request.NoteRequest
import com.elkusnandi.generalnote.response.NotesResponse
import org.springframework.http.HttpStatus
import org.springframework.security.authorization.AuthorizationDeniedException
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
        val note = noteRepository.findById(noteId).orElseThrow {
            UserFaultException(HttpStatus.NOT_FOUND, "Not found")
        }

        if (note.owner?.id != ownerId) {
            throw AuthorizationDeniedException("Cannot access another user note")
        }

        return NotesResponse(
            note.id,
            note.title,
            note.content
        )
    }

    override fun upsertNote(ownerId: Long, note: NoteRequest): NotesResponse {
        // Need to get user if not it will cause error
        val currentUser = userRepository.findById(ownerId).get()

        if (note.id != -1L) {
            val currentNote = noteRepository.findById(note.id)

            if (currentNote.get().owner?.id != ownerId) {
                throw AuthorizationDeniedException("Cannot edit another user note")
            }
        }

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

    override fun deleteNote(ownerId: Long, noteId: Long) {
        val currentNote = noteRepository.findById(noteId)

        if (currentNote.get().owner?.id != ownerId) {
            throw AuthorizationDeniedException("Cannot delete another user note")
        }

        return noteRepository.deleteById(noteId)
    }
}