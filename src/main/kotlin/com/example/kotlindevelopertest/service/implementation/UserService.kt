package com.example.kotlindevelopertest.service.implementation

import com.example.kotlindevelopertest.exceptions.BadRequest
import com.example.kotlindevelopertest.model.User
import com.example.kotlindevelopertest.repository.UserRepository
import com.example.kotlindevelopertest.service.interfaces.IUserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository): IUserService {

    fun create(name: String, email: String): User {
        val emailExists = userRepository.existsByEmail(email)

        if(emailExists) {
            throw BadRequest("""Duplicate e-mail: $email""")
        }

        val user = User(name = name, email = email)
        return userRepository.save(user)
    }

    fun searchUsersByName(query: String, pageable: Pageable): Page<User> {
        return userRepository.findByNameStartingWithIgnoreCase(query, pageable)
    }}