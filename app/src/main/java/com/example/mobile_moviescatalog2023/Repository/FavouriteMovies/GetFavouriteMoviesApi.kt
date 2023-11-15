package com.example.mobile_moviescatalog2023.Repository.FavouriteMovies

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface GetFavouriteMoviesApi {
    @Headers("Content-Type: application/json")
    @GET("favorites")
    suspend fun getFavoriteMovies(@Header("Authorization") token: String): FavoriteMoviesResponse
}