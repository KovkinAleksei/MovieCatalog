package com.example.mobile_moviescatalog2023.Domain

import com.example.mobile_moviescatalog2023.Repository.MovieDetails.ReviewDetails
import com.example.mobile_moviescatalog2023.Repository.Review.ReviewBody
import com.example.mobile_moviescatalog2023.Repository.Review.ReviewRequest
import com.example.mobile_moviescatalog2023.ViewModel.FeedbackViewModel
import com.example.mobile_moviescatalog2023.ViewModel.MovieDescriptionViewModel

class FeedbackUseCase {
    var movieId = ""
    var isNewFeedback = false
    var message = ""
    var isAnonymous = false
    var rating = 0
    var myReview: ReviewDetails? = null

    private val reviewRequest = ReviewRequest()

    // Заполнение данных отзыва
    fun getValues(review: ReviewDetails?, id: String) {
        movieId = id

        if (review == null) {
            isNewFeedback = true
            message = ""
            isAnonymous = false
            rating = 0

            return
        }

        isNewFeedback = false
        myReview = review

        rating = review.rating.toInt()
        isAnonymous = review.isAnonymous
        message = review.reviewText ?: ""
    }

    // Изменение анонимности отзыва
    fun changeAnonymous() {
        isAnonymous = !isAnonymous
        myReview?.isAnonymous = isAnonymous
    }

    // Сохранение отзыва
    suspend fun saveFeedback(descriptionVm: MovieDescriptionViewModel, review: ReviewBody) {
        if (isNewFeedback)
            addFeedback(descriptionVm, review)
        else
            editFeedback(descriptionVm, review)
    }

    // Добавление нового отзыва
    private suspend fun addFeedback(descriptionVm: MovieDescriptionViewModel, review: ReviewBody) {
        reviewRequest.addReview(movieId, review)
        descriptionVm.updateFeedback()
    }

    // Редактирование отзыва
    private suspend fun editFeedback(descriptionVm: MovieDescriptionViewModel, review: ReviewBody) {
        if (review.isAnonymous) {
            reviewRequest.deleteReview(movieId, descriptionVm.myReview?.id ?: "")
            reviewRequest.addReview(movieId, review)
        } else {
            reviewRequest.editReview(movieId, review, myReview?.id ?: "")
        }

        descriptionVm.updateFeedback()
    }

    // Отмена редактирования отзыва
    fun cancel() {
        rating = 0
        isAnonymous = false
        message = ""
    }

    // Проверка на заполнение
    fun checkFilling(vm: FeedbackViewModel): Boolean {
        return vm.rating.value != 0 && vm.message.value != ""
    }
}