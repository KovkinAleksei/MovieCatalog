package com.example.mobile_moviescatalog2023.Repository.Registration

import kotlinx.serialization.Serializable

@Serializable
class RegistrationBody(
    val userName: String,
    val name: String,
    val password: String,
    val email: String,
    var birthDate: String,
    val gender: Int
)