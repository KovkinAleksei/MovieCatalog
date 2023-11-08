package com.example.mobile_moviescatalog2023.Repository.MovieDetails


import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface MovieDetailsApi {
    @Headers("Content-Type: application/json")
    @GET("movies/details/{id}")
    suspend fun getMovies(@Path("id") id: String, @Header("Authorization") token: String) : MovieDetailsResponse
}