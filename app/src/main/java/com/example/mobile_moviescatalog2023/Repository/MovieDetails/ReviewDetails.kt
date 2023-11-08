package com.example.mobile_moviescatalog2023.Repository.MovieDetails

@kotlinx.serialization.Serializable
class ReviewDetails(
    val id: String,
    val rating: Float,
    val reviewText: String?,
    val isAnonymous: Boolean,
    val createDateTime: String,
    val author: ReviewAuthor
)