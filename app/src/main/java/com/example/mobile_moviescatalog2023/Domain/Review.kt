package com.example.mobile_moviescatalog2023.Domain

import kotlinx.serialization.Serializable

@Serializable
class Review (
    val id: String,
    val rating: Int
)