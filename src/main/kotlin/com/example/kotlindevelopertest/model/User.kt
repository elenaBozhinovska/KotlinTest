package com.example.kotlindevelopertest.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
 class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false, unique = true)
    lateinit var email: String

    @Column(nullable = false)
    lateinit var name: String

    constructor()

    constructor(email: String, name: String) {
        this.email = email
        this.name = name
    }
}