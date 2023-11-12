package com.example.mobile_moviescatalog2023.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mobile_moviescatalog2023.Repository.MovieDetails.ReviewDetails
import com.example.mobile_moviescatalog2023.Repository.RetrofitImplementation
import com.example.mobile_moviescatalog2023.Repository.Review.ReviewBody
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedbackViewModel: ViewModel() {
    val rating = mutableStateOf(0)
    val isAnonymous = mutableStateOf(false)
    val message = mutableStateOf("")
    private var isNewFeedback = false
    private var movieId = ""
    private var myReview: ReviewDetails? = null
    val isFilled = mutableStateOf(false)

    // Заполнение данных отзыва
    fun getValues(review: ReviewDetails?, id: String) {
        movieId = id

        if (review == null){
            isNewFeedback = true
            message.value = ""
            isAnonymous.value = false
            rating.value = 0

            return
        }

        isNewFeedback = false
        myReview = review

        rating.value = review.rating.toInt()
        isAnonymous.value = review.isAnonymous
        message.value = review.reviewText ?: ""
    }

    // Изменение анонимности отзыва
    fun changeAnonymous() {
        isAnonymous.value = !isAnonymous.value
    }

    // Сохранение отзыва
    fun saveFeedback() {
        if (isNewFeedback)
            addFeedback()
        else
            editFeedback()
    }

    // Добавление нового отзыва
    private fun addFeedback() {
        val retrofit = RetrofitImplementation()
        val api = retrofit.addReviewImplementation()

        CoroutineScope(Dispatchers.Default).launch {
            val body = ReviewBody(message.value, rating.value, isAnonymous.value)
            api.addReview(id = movieId, token = "Bearer ${AuthorizationToken.token}", body)
        }
    }

    // Редактирование отзыва
    private fun editFeedback() {
        val retrofit = RetrofitImplementation()
        val api = retrofit.editReviewImplementation()

        CoroutineScope(Dispatchers.Default).launch {
            val body = ReviewBody(message.value, rating.value, isAnonymous.value)
            api.editReview(
                movieId = movieId,
                reviewId = myReview?.id ?: "",
                token = "Bearer ${AuthorizationToken.token}",
                body = body
            )
        }
    }

    // Отмена редактирования отзыва
    fun cancel() {
        rating.value = 0
        isAnonymous.value = false
        message.value = ""
        isFilled.value = false
    }

    // Проверка на заполнение
    fun checkFilling() {
        isFilled.value = (rating.value != 0 && message.value != "")
    }
}