package com.example.mobile_moviescatalog2023.Repository.Review

import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface DeleteReviewApi {
    @Headers("Content-Type: application/json")
    @DELETE("movie/{movieId}/review/{id}/delete")
    suspend fun deleteReview(
        @Path("movieId") movieId: String,
        @Path("id") reviewId: String,
        @Header("Authorization") token: String
    )
}