package com.elkusnandi.generalnote.entity

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
    @Column(name = "content")
    @Lob
    val content: String = "",
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val owner: Users? = null
)