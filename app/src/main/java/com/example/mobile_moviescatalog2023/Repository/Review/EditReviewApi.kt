package com.example.mobile_moviescatalog2023.Repository.Review

import retrofit2.http.*

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