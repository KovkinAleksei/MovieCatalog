package com.example.mobile_moviescatalog2023.Repository.Review

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PUT
import retrofit2.http.Path

interface EditReviewApi {
    @Headers("Content-Type: application/json")
    @PUT("movie/{movieId}/review/{id}/edit")
    suspend fun editReview(
        @Header("Authorization") token: String,
        @Path("movieId") movieId: String,
        @Path("id") reviewId: String,
        @Body body: ReviewBody
    )
}