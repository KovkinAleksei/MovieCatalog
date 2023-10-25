package com.example.mobile_moviescatalog2023.Repository.Movies

import com.example.mobile_moviescatalog2023.Repository.Login.LoginBody
import com.example.mobile_moviescatalog2023.Repository.TokenResponse
import com.example.mobile_moviescatalog2023.ViewModel.AuthorizationToken
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface MovieApi {
    @Headers("Content-Type: application/json")
    @GET("movies/1")
    suspend fun getMovies(@Header("Authorization") token: String) : MoviesResponse
}