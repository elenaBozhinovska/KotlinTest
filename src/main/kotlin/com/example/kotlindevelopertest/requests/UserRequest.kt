package com.example.kotlindevelopertest.requests
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UserRequest( @field:NotBlank(message = "Email is required")
                        @field:Email(message = "Email format is invalid")
                        val email: String,
                        @field:NotBlank(message = "Name is required")
                        val name: String,
                        @field:NotBlank(message = "Password is required")
                        @field:Size(min = 6, message = "Password must be at least 6 characters")
                        val password: String)