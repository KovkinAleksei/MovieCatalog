package com.example.mobile_moviescatalog2023.Repository.Movies

import com.example.mobile_moviescatalog2023.Domain.Movie
import com.example.mobile_moviescatalog2023.Domain.PageInfo

@kotlinx.serialization.Serializable
class MoviesResponse (
    val pageInfo: PageInfo,
    val movies: List<Movie>
)