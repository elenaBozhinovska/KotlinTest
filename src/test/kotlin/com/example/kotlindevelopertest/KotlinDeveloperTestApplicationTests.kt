package com.example.kotlindevelopertest.api
import com.example.kotlindevelopertest.model.User
import com.example.kotlindevelopertest.service.interfaces.IUserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
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

    @Configuration
    class TestConfig {
        @Bean
        fun userService(): IUserService {
            return mock(IUserService::class.java)
        }
    }

    @Test
    fun `createUser should return 200 OK and user data`() {
        val user = User(name = "Alice", email = "alice@example.com")
        `when`(userService.create("Alice", "alice@example.com")).thenReturn(user)

        mockMvc.perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name", `is`("Alice")))
            .andExpect(jsonPath("$.email", `is`("alice@example.com")))
    }

}