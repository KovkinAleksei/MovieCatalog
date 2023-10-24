package com.example.mobile_moviescatalog2023.Repository

import com.example.mobile_moviescatalog2023.ViewModel.RegistrationData
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationApi {
    @POST("account/register")
    suspend fun register(@Body body: RegistrationBody) : TokenResponse
}