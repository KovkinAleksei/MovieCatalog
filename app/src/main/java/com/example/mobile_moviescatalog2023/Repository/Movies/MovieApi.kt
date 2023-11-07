package com.example.mobile_moviescatalog2023.Repository.Movies

import com.example.mobile_moviescatalog2023.Repository.Login.LoginBody
import com.example.mobile_moviescatalog2023.Repository.TokenResponse
import com.example.mobile_moviescatalog2023.ViewModel.AuthorizationToken
import retrofit2.http.*

interface MovieApi {
    @Headers("Content-Type: application/json")
    @GET("movies/{id}")
    suspend fun getMovies(@Path("id") id: Int, @Header("Authorization") token: String) : MoviesResponse
}