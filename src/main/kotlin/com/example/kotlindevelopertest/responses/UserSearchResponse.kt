package com.example.kotlindevelopertest.responses

data class UsersSearchResult(
    val users: List<UserResponse>,
    val total: Long
)