package com.example.mobile_moviescatalog2023.Repository.Login

import com.example.mobile_moviescatalog2023.Repository.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("account/login")
    suspend fun login(@Body body: LoginBody) : TokenResponse
}