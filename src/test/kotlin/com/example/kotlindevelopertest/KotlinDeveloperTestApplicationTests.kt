package com.example.kotlindevelopertest

import com.example.kotlindevelopertest.api.UserController
import com.example.kotlindevelopertest.model.User
import com.example.kotlindevelopertest.requests.UserRequest
import com.example.kotlindevelopertest.service.interfaces.IUserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(UserController::class)
@ContextConfiguration(classes = [UserController::class, UserControllerIntegrationTest.TestConfig::class])
class UserControllerIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var userService: IUserService

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Configuration
    class TestConfig {
        @Bean
        fun userService(): IUserService {
            return mock(IUserService::class.java)
        }

        @Bean
        fun passwordEncoder(): PasswordEncoder {
            return mock(PasswordEncoder::class.java)
        }
    }

    @Test
    fun `createUser should return 202 Accepted`() {
        val encodedPassword = "encodedPassword123"
        `when`(passwordEncoder.encode("password123")).thenReturn(encodedPassword)
        val user = UserRequest(name = "Alice", email = "alice@example.com", password = "password123")
        `when`(userService.create(name = "Alice", email = "alice@example.com", encodedPassword = encodedPassword))
            .thenReturn(User(name = user.name, password = user.password, email = user.email))

        mockMvc.perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
        )
            .andExpect(status().isAccepted)
    }

}