package com.elkusnandi.generalnote.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/notes")
class NoteController {

    @GetMapping()
    fun getNotes() : String {
        return "notes"
    }

}