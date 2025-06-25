package com.elkusnandi.generalnote.service

import com.elkusnandi.generalnote.request.NoteRequest
import com.elkusnandi.generalnote.response.NotesResponse

interface NoteService {

    fun getNotes(ownerId: Long): List<NotesResponse>

    fun getNote(noteId: Long): NotesResponse

    fun createNote(ownerId: Long, note: NoteRequest): NotesResponse

    fun updateNote(ownerId: Long, note: NoteRequest): NotesResponse

    fun deleteNote(ownerId: Long, noteId: Long)

}