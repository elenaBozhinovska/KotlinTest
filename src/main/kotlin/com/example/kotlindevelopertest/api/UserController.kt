package com.example.kotlindevelopertest.api

import com.example.kotlindevelopertest.requests.UserRequest
import com.example.kotlindevelopertest.responses.UserResponse
import com.example.kotlindevelopertest.responses.UsersSearchResult
import com.example.kotlindevelopertest.service.interfaces.IUserService
import jakarta.validation.Valid
import org.apache.coyote.BadRequestException
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(private val userService: IUserService, val passwordEncoder: PasswordEncoder) {

    @PostMapping
    fun createUser(@RequestBody @Valid userRequest: UserRequest): ResponseEntity<Any> {
            val encodedPassword = passwordEncoder.encode(userRequest.password)
            val savedUser = userService.create(userRequest.name, userRequest.email, encodedPassword)
            return  ResponseEntity.accepted().build()
    }

    @GetMapping
    fun searchUsers(
        @RequestParam query: String,
        @RequestParam limit: Int
    ): UsersSearchResult {
        val pageable = PageRequest.of(0, limit)
        val users = userService.searchUsersByName(query, pageable)
        val userResponses = users.map { UserResponse(it.email, it.name) }.toList()
        return UsersSearchResult(users = userResponses, total = userService.count())
    }

}