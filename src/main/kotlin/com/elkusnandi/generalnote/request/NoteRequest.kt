package com.elkusnandi.generalnote.request

data class NoteRequest(
    val id: Long = -1,
    val title: String = "",
    val content: String = "",
)