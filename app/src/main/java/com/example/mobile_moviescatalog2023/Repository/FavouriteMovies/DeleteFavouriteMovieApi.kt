package com.example.mobile_moviescatalog2023.Repository.FavouriteMovies

import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface DeleteFavouriteMovieApi {
    @Headers("Content-Type: application/json")
    @DELETE("favorites/{id}/delete")
    suspend fun deleteFavoriteMovies(@Path("id") id: String, @Header("Authorization") token: String)
}