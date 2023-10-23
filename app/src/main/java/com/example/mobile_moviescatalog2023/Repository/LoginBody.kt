package com.example.mobile_moviescatalog2023.Repository

@kotlinx.serialization.Serializable
data class LoginBody (
    val username: String,
    val password: String
)