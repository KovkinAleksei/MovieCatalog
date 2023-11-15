package com.example.mobile_moviescatalog2023.Repository.Review

import retrofit2.http.*

interface AddReviewApi {
    @Headers("Content-Type: application/json")
    @POST("movie/{id}/review/add")
    suspend fun addReview(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Body body: ReviewBody
    )
}