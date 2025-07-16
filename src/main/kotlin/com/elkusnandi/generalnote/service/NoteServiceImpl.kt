package com.elkusnandi.generalnote.service

import com.elkusnandi.generalnote.entity.Notes
import com.elkusnandi.generalnote.exception.UserFaultException
import com.elkusnandi.generalnote.mapper.NoteMapper
import com.elkusnandi.generalnote.repository.NoteRepository
import com.elkusnandi.generalnote.repository.UserRepository
import com.elkusnandi.generalnote.request.NoteRequest
import com.elkusnandi.generalnote.response.NotesResponse
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.security.authorization.AuthorizationDeniedException
import org.springframework.stereotype.Service

@Service
class NoteServiceImpl(
    private val noteRepository: NoteRepository,
    private val userRepository: UserRepository
) : NoteService {

    override fun getNotes(ownerId: Long): List<NotesResponse> {
        return noteRepository.findByOwnerId(ownerId).map {
            NoteMapper.INSTANCE.notesToResponse(it)
        }
    }

    @PostAuthorize("returnObject.ownerId == principal.username")
    override fun getNote(noteId: Long): NotesResponse {
        val note = noteRepository.findById(noteId).orElseThrow {
            UserFaultException(HttpStatus.NOT_FOUND, "Not found")
        }

        return NoteMapper.INSTANCE.notesToResponse(note)
    }

    override fun createNote(ownerId: Long, note: NoteRequest): NotesResponse {
        // Need to get user if not it will cause error
        val currentUser = userRepository.findById(ownerId).get()

        return noteRepository.save(
            Notes(
                id = note.id,
                title = note.title,
                content = note.content,
                owner = currentUser
            )
        ).let {
            NoteMapper.INSTANCE.notesToResponse(it)
        }
    }

    override fun updateNote(ownerId: Long, note: NoteRequest): NotesResponse {
        // Check note ownership
        val currentNote = noteRepository.findById(note.id)

        if (currentNote.get().owner?.id != ownerId) {
            throw AuthorizationDeniedException("Cannot edit another user note")
        }

        // Need to get user if not it will cause error
        val currentUser = userRepository.findById(ownerId).get()

        return noteRepository.save(
            Notes(
                id = note.id,
                title = note.title,
                content = note.content,
                owner = currentUser
            )
        ).let {
            NoteMapper.INSTANCE.notesToResponse(it)
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