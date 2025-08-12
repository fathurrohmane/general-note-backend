package com.elkusnandi.playground.service

import com.elkusnandi.playground.request.NoteRequest
import com.elkusnandi.playground.response.NotesResponse

interface NoteService {

    fun getNotes(ownerId: Long): List<NotesResponse>

    fun getNote(noteId: Long): NotesResponse

    fun createNote(ownerId: Long, note: NoteRequest): NotesResponse

    fun updateNote(ownerId: Long, note: NoteRequest): NotesResponse

    fun deleteNote(ownerId: Long, noteId: Long)

}