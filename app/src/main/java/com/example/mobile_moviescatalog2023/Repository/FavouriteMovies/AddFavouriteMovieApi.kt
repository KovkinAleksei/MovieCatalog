package com.example.mobile_moviescatalog2023.Repository.FavouriteMovies

import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface AddFavouriteMovieApi {
    @Headers("Content-Type: application/json")
    @POST("favorites/{id}/add")
    suspend fun addFavouriteMovie(@Path("id") id: String, @Header("Authorization") token: String)
}