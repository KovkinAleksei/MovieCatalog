package com.example.mobile_moviescatalog2023.Repository.FavouriteMovies

import com.example.mobile_moviescatalog2023.Repository.Movies.Movie

@kotlinx.serialization.Serializable
class FavoriteMoviesResponse(
    val movies: List<Movie>
)