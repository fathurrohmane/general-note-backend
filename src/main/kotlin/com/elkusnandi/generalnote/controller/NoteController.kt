package com.elkusnandi.generalnote.controller

import com.elkusnandi.generalnote.common.base.BaseResponse
import com.elkusnandi.generalnote.request.CreateNoteRequest
import com.elkusnandi.generalnote.request.UpdateNoteRequest
import com.elkusnandi.generalnote.response.NotesResponse
import com.elkusnandi.generalnote.service.NoteService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/notes")
class NoteController(
    private val noteService: NoteService
) {

    @Operation(
        summary = "Get all current user notes",
        description = "Get all current user notes based on user id from access token"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "List of notes"),
            ApiResponse(responseCode = "403", description = "Access denied")
        ]
    )
    @GetMapping()
    fun getNotes(@AuthenticationPrincipal userDetails: UserDetails): BaseResponse<List<NotesResponse>> {
        return BaseResponse(data = noteService.getNotes(userDetails.username.toLong()), status = HttpStatus.OK)
    }

    @Operation(
        summary = "Get single note",
        description = "Get a note based on noteId and user id from access token"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Note"),
            ApiResponse(responseCode = "403", description = "Access denied")
        ]
    )
    @GetMapping("/{noteId}")
    fun getNote(
        @AuthenticationPrincipal userDetails: UserDetails,
        @PathVariable(name = "noteId") noteId: Long
    ): BaseResponse<NotesResponse> {
        return BaseResponse(data = noteService.getNote(noteId), status = HttpStatus.OK)
    }

    @Operation(
        summary = "Create new note",
        description = "Create new note for current user based on user id from access token"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Newly created note"),
            ApiResponse(responseCode = "403", description = "Access denied")
        ]
    )
    @PostMapping
    fun createNote(
        @AuthenticationPrincipal userDetails: UserDetails,
        @RequestBody note: CreateNoteRequest
    ): BaseResponse<NotesResponse> {
        return BaseResponse(
            data = noteService.createNote(ownerId = userDetails.username.toLong(), note), status = HttpStatus.OK
        )
    }

    @Operation(
        summary = "Update note",
        description = "Update note for current user based on noteId and user id from access token"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Updated note"),
            ApiResponse(responseCode = "403", description = "Access denied")
        ]
    )
    @PutMapping("/{noteId}")
    fun updateNote(
        @AuthenticationPrincipal userDetails: UserDetails,
        @PathVariable noteId: Long,
        @RequestBody note: UpdateNoteRequest
    ): BaseResponse<NotesResponse> {
        return BaseResponse(
            data = noteService.updateNote(ownerId = userDetails.username.toLong(), note.copy(id = noteId)), status = HttpStatus.OK
        )
    }

    @Operation(
        summary = "Delete note",
        description = "Delete note for current user based on noteId and user id from access token"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = ""),
            ApiResponse(responseCode = "403", description = "Access denied")
        ]
    )
    @DeleteMapping("/{noteId}")
    fun deleteNote(@AuthenticationPrincipal userDetails: UserDetails, @PathVariable noteId: String): BaseResponse<*> {
        noteService.deleteNote(ownerId = userDetails.username.toLong(), noteId = noteId.toLong())
        return BaseResponse(data = null, status = HttpStatus.OK)
    }

}