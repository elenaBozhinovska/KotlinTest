package com.example.kotlindevelopertest.requests
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class UserRequest( @field:NotBlank(message = "Email is required")
                        @field:Email(message = "Email format is invalid")
                        val email: String,
                        @field:NotBlank(message = "Name is required")
                        val name: String)