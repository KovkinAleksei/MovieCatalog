package com.example.mobile_moviescatalog2023.Repository.FavouriteMovies

import com.example.mobile_moviescatalog2023.Repository.MovieDetails.MovieDetailsResponse
import com.example.mobile_moviescatalog2023.Repository.RetrofitImplementation
import com.example.mobile_moviescatalog2023.ViewModel.AuthorizationToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavouriteMovieRequest {
    // Запрос для получения любимых фильмов
    suspend fun getFavouriteMovies(): FavoriteMoviesResponse {
        val retrofit = RetrofitImplementation()
        val api = retrofit.getFavouriteMoviesImplementation()

        return withContext(Dispatchers.Default) {
            api.getFavoriteMovies(token = "Bearer ${AuthorizationToken.token}")
        }
    }

    // Запрос для добавления фильма в любимые
    suspend fun addFavouriteMovie(movieDetails: MovieDetailsResponse?) {
        val retrofit = RetrofitImplementation()
        val api = retrofit.addFavouriteMovieImplementation()

        api.addFavouriteMovie(
            id = movieDetails?.id ?: "",
            token = "Bearer ${AuthorizationToken.token}"
        )
    }

    // Запрос для удаления фильма из любимых
    suspend fun deleteMovieFromFavourite(movieDetails: MovieDetailsResponse?) {
        val retrofit = RetrofitImplementation()
        val api = retrofit.deleteFavouriteMoviesImplementation()

        api.deleteFavoriteMovies(
            id = movieDetails?.id ?: "",
            token = "Bearer ${AuthorizationToken.token}"
        )
    }
}