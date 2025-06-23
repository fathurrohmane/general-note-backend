package com.elkusnandi.generalnote.entity

import jakarta.persistence.*

@Entity
@Table(name = "roles")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long = -1,
    @Column(name = "name")
    var name: String = "",
) {
    @ManyToMany(mappedBy = "roles")
    var users: MutableSet<Users> = mutableSetOf()
}