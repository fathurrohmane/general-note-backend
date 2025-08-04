package com.elkusnandi.generalnote.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "user_token")
class UserToken(
    @Id
    @Column(name = "id")
    val id: UUID = UUID.randomUUID(),
    @Column(name = "token_id")
    val tokenId: String = "",
    @Column(name = "user_agent")
    val userAgent: String = "",
    @Column(name = "created_at")
    val createdDate: LocalDateTime = LocalDateTime.now()
) {

    @ManyToOne
    @JoinColumn(name = "user_id")
    lateinit var user: Users

}