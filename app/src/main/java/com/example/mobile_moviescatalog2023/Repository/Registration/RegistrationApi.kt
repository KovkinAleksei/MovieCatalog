package com.example.mobile_moviescatalog2023.Repository.Registration

import com.example.mobile_moviescatalog2023.Repository.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationApi {
    @POST("account/register")
    suspend fun register(@Body body: RegistrationBody) : TokenResponse
}