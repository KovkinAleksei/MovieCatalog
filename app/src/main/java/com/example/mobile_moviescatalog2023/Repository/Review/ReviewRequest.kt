package com.example.mobile_moviescatalog2023.Repository.Review

import com.example.mobile_moviescatalog2023.Repository.RetrofitImplementation
import com.example.mobile_moviescatalog2023.ViewModel.AuthorizationToken

class ReviewRequest {
    // Запрос для удаления отзыва
    suspend fun deleteReview(movieId: String, reviewId: String) {
        val retrofit = RetrofitImplementation()
        val api = retrofit.deleteReviewImplementation()

        api.deleteReview(
            movieId = movieId,
            reviewId = reviewId,
            token = "Bearer ${AuthorizationToken.token}"
        )
    }

    // Запрос для добавления отзыва
    suspend fun addReview(movieId: String, review: ReviewBody) {
        val retrofit = RetrofitImplementation()
        val api = retrofit.addReviewImplementation()

        api.addReview(id = movieId, token = "Bearer ${AuthorizationToken.token}", review)
    }

    // Запрос для редактирования отзыва
    suspend fun editReview(movieId: String, review: ReviewBody, reviewId: String) {
        val retrofit = RetrofitImplementation()
        val api = retrofit.editReviewImplementation()

        api.editReview(
            movieId = movieId,
            reviewId = reviewId,
            token = "Bearer ${AuthorizationToken.token}",
            body = review
        )
    }
}