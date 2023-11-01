package com.example.mobile_moviescatalog2023.Repository.Login

import kotlinx.serialization.Serializable

@Serializable
class LogoutResponse (
    val token: String,
    val message: String
)