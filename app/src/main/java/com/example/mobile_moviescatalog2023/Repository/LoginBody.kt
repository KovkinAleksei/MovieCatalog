package com.example.mobile_moviescatalog2023.Repository

import kotlinx.serialization.Serializable

@Serializable
data class LoginBody (
    val username: String,
    val password: String
)