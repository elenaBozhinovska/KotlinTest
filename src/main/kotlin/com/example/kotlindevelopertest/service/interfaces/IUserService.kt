package com.example.kotlindevelopertest.service.interfaces

import com.example.kotlindevelopertest.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface IUserService {
    fun create(name: String, email: String): User
    fun searchUsersByName(query: String, pageable: Pageable): Page<User>
    fun count(): Long
}