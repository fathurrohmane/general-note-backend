package com.elkusnandi.playground.response

import com.fasterxml.jackson.annotation.JsonIgnore

data class NotesResponse(
    val id: Long,
    val title: String,
    val content: String,
    @JsonIgnore
    val ownerId: String
)