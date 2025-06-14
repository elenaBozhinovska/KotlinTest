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

    override fun create(name: String, email: String, encodedPassword: String): User {
        val emailExists = userRepository.existsByEmail(email)

        if(emailExists) {
            throw BadRequest("""Duplicate e-mail: $email""")
        }

        val user = User(name = name, email = email, password = encodedPassword)
        return userRepository.save(user)
    }

    override fun searchUsersByName(query: String, pageable: Pageable): Page<User> {
        return userRepository.findByNameStartingWithIgnoreCase(query, pageable)
    }

    override fun count() = userRepository.count()

}