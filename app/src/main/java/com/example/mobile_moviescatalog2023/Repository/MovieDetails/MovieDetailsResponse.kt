package com.example.mobile_moviescatalog2023.Repository.MovieDetails

import com.example.mobile_moviescatalog2023.Repository.Movies.Genre

@kotlinx.serialization.Serializable
class MovieDetailsResponse(
    val id: String,
    val name: String?,
    val poster: String?,
    val year: Int,
    val country: String?,
    val genres: List<Genre>,
    val reviews: List<ReviewDetails>?,
    val time: Int,
    val tagline: String?,
    val description: String?,
    val director: String?,
    val budget: Long?,
    val fees: Long?,
    val ageLimit: Int
)