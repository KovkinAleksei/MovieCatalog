package com.example.mobile_moviescatalog2023.Repository.Movies

import kotlinx.serialization.Serializable

@Serializable
class Review(
    val id: String,
    val rating: Int
)