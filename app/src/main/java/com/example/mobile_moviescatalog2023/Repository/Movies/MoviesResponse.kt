package com.example.mobile_moviescatalog2023.Repository.Movies

@kotlinx.serialization.Serializable
class MoviesResponse (
    val pageInfo: PageInfo,
    val movies: List<Movie>
)