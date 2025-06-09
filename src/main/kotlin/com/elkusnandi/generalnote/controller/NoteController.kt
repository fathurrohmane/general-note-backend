package com.elkusnandi.generalnote.controller

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/notes")
class NoteController {

    @GetMapping()
    fun getNotes(@AuthenticationPrincipal userDetails: UserDetails) : String {
        return "${userDetails.username}'s notes"
    }

}