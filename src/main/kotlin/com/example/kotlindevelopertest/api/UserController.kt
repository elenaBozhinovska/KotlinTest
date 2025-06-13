package com.example.kotlindevelopertest.api

import com.example.kotlindevelopertest.model.User
import com.example.kotlindevelopertest.responses.UserResponse
import com.example.kotlindevelopertest.responses.UsersSearchResult
import com.example.kotlindevelopertest.service.interfaces.IUserService
import org.apache.coyote.BadRequestException
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(private val userService: IUserService) {

    @PostMapping
    fun createUser(@RequestBody user: User): ResponseEntity<Any> {
        try {
            val savedUser = userService.create(user.name, user.email)
            return ResponseEntity.ok(savedUser)
        } catch (badRequestException: BadRequestException) {
            return ResponseEntity.badRequest().body(mapOf("error" to badRequestException.message))
        }
        catch (exception: Exception) {
            return ResponseEntity.badRequest().body(mapOf("error" to exception.message))
        }
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