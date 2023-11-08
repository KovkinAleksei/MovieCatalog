package com.example.mobile_moviescatalog2023.Repository.MovieDetails

@kotlinx.serialization.Serializable
class ReviewAuthor(
    val userId: String,
    val nickName: String?,
    val avatar: String?
)