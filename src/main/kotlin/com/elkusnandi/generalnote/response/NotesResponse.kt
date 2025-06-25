package com.elkusnandi.generalnote.response

import com.fasterxml.jackson.annotation.JsonIgnore

data class NotesResponse(
    val id: Long,
    val title: String,
    val content: String,
    @JsonIgnore
    val ownerId: String
)