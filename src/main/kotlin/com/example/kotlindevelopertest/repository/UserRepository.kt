package com.example.kotlindevelopertest.repository

import com.example.kotlindevelopertest.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun existsByEmail(email: String): Boolean
    fun findByNameStartingWithIgnoreCase(query: String, pageable: Pageable): Page<User>
}