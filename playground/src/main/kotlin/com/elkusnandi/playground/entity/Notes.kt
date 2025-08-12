package com.elkusnandi.playground.entity

import com.elkusnandi.common.entity.Users
import jakarta.persistence.*

@Entity
@Table(name = "notes")
data class Notes(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = -1,
    @Column(name = "title")
    val title: String = "",
    @Column(name = "content", columnDefinition = "TEXT")
    val content: String = "",
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    val owner: Users? = null
)