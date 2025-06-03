package com.elkusnandi.generalnote.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class Users(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = -1,
    @Column(name = "username")
    val userName: String = "",
    @Column(name = "password")
    val password: String = ""
)