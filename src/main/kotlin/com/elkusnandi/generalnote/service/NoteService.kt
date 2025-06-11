package com.elkusnandi.generalnote.service

import com.elkusnandi.generalnote.entity.Notes
import com.elkusnandi.generalnote.response.NotesResponse

interface NoteService {

    fun getNotes(ownerId: Long): List<NotesResponse>

    fun upsertNote(note: Notes): NotesResponse

    fun deleteNote(noteId: Long)

}