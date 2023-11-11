package com.example.mobile_moviescatalog2023.Repository.Review

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface AddReviewApi {
    @Headers("Content-Type: application/json")
    @POST("movie/{id}/review/add")
    suspend fun addReview(@Path("id") id: String, @Header("Authorization") token: String, @Body body: ReviewBody)
}