package com.elkusnandi.generalnote.controller

import com.elkusnandi.generalnote.common.base.BaseResponse
import com.elkusnandi.generalnote.entity.Notes
import com.elkusnandi.generalnote.entity.Users
import com.elkusnandi.generalnote.request.NoteRequest
import com.elkusnandi.generalnote.response.NotesResponse
import com.elkusnandi.generalnote.service.NoteService
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/notes")
class NoteController(
    private val noteService: NoteService
) {

    @GetMapping()
    fun getNotes(@AuthenticationPrincipal userDetails: UserDetails): BaseResponse<List<NotesResponse>> {
        return BaseResponse(data = noteService.getNotes(userDetails.username.toLong()), status = HttpStatus.OK)
    }

    @PostMapping
    fun createNote(
        @AuthenticationPrincipal userDetails: UserDetails,
        @RequestBody note: NoteRequest
    ): BaseResponse<NotesResponse> {
        return BaseResponse(
            data = noteService.upsertNote(
                Notes(
                    note.id,
                    note.title,
                    note.content,
                    Users(id = userDetails.username.toLong())
                )
            ), status = HttpStatus.OK
        )
    }

    @PutMapping("/{noteId}")
    fun updateNote(
        @AuthenticationPrincipal userDetails: UserDetails,
        @PathVariable noteId: Long,
        @RequestBody note: NoteRequest
    ): BaseResponse<NotesResponse> {
        return BaseResponse(
            data = noteService.upsertNote(
                Notes(
                    note.id,
                    note.title,
                    note.content,
                    Users(id = userDetails.username.toLong())
                )
            ), status = HttpStatus.OK
        )
    }

    @DeleteMapping("/{noteId}")
    fun deleteNote(@PathVariable noteId: String) {
        noteService.deleteNote(noteId = noteId.toLong())
    }

}