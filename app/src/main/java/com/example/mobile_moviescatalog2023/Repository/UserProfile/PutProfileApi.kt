package com.example.mobile_moviescatalog2023.Repository.UserProfile

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PUT

interface PutProfileApi {
    @Headers("Content-Type: application/json")
    @PUT("account/profile")
    suspend fun putProfile(@Header("Authorization") token: String, @Body body: UserProfile)
}