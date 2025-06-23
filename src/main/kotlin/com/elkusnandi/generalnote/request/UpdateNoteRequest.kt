package com.elkusnandi.generalnote.request

import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.v3.oas.annotations.media.Schema

interface NoteRequest {
    val id: Long
    val title: String
    val content: String
}

data class UpdateNoteRequest(
    @Schema(
        description = "Note id",
        example = "1",
    )
    override val id: Long = -1,
    @Schema(
        description = "title of the note",
        example = "Note title",
    )
    override val title: String = "",
    @Schema(
        description = "Content of the note",
        example = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
    )
    override val content: String = "",
) : NoteRequest

data class CreateNoteRequest(
    @Schema(
        description = "title of the note",
        example = "Note title",
    )
    override val title: String = "",
    @Schema(
        description = "Content of the note",
        example = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
    )
    override val content: String = "",
) : NoteRequest {

    @get:JsonIgnore
    override val id: Long
        get() = -1
}
