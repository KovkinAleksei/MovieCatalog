package com.example.mobile_moviescatalog2023.Repository.UserProfile

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface GetProfileApi {
    @Headers("Content-Type: application/json")
    @GET("account/profile")
    suspend fun getProfile(@Header("Authorization") token: String): UserProfile
}