package com.example.mobile_moviescatalog2023.Repository.Login

import retrofit2.http.POST

interface LogoutApi {
    @POST("account/logout")
    suspend fun logout(): LogoutResponse
}