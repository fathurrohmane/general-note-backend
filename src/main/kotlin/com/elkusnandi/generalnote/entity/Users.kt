package com.elkusnandi.generalnote.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class Users(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long = -1,
    @Column(name = "username")
    var userName: String = "",
    @Column(name = "password")
    var password: String = ""
) {
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "users_id")],
        inverseJoinColumns = [JoinColumn(name = "roles_id")]
    )
    var roles: MutableSet<Role> = mutableSetOf()
}