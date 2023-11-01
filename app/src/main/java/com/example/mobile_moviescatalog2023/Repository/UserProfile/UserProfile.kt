package com.example.mobile_moviescatalog2023.Repository.UserProfile

import kotlinx.serialization.Serializable

@Serializable
class UserProfile(
    val id: String,
    val nickName: String,
    val email: String,
    val avatarLink: String?,
    val name: String,
    val birthDate: String,
    val gender: Int
)