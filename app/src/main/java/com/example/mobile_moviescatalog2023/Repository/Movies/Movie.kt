package com.example.mobile_moviescatalog2023.Repository.Movies

import kotlinx.serialization.Serializable

@Serializable
class Movie(
    val id: String = "",
    val name: String = "",
    val poster: String = "",
    val year: Int = 0,
    val country: String = "",
    val genres: List<Genre> = listOf(Genre("", "")),
    val reviews: List<Review> = listOf(Review("", 0))
)