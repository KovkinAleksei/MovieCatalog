package com.example.mobile_moviescatalog2023.Repository.Review

@kotlinx.serialization.Serializable
class ReviewBody(
    val reviewText: String,
    val rating: Int,
    val isAnonymous: Boolean
)