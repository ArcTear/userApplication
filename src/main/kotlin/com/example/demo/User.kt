package com.example.demo

import jakarta.persistence.*

@Entity
@Table(name = "users")
open class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long = 0,

    @Column(nullable = false)
    open var name: String = "",

    @Column(nullable = false, unique = true)
    open var email: String = ""
) {
    // No-arg constructor required by Hibernate
    constructor() : this(0, "", "")
}
