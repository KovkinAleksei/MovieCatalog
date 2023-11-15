package com.example.mobile_moviescatalog2023.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mobile_moviescatalog2023.Domain.FeedbackUseCase
import com.example.mobile_moviescatalog2023.Repository.MovieDetails.ReviewDetails
import com.example.mobile_moviescatalog2023.Repository.Review.ReviewBody
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedbackViewModel : ViewModel() {
    val rating = mutableStateOf(0)
    val isAnonymous = mutableStateOf(false)
    val message = mutableStateOf("")
    val isFilled = mutableStateOf(false)

    private val feedbackUseCase = FeedbackUseCase()

    // Заполнение данных отзыва
    fun getValues(review: ReviewDetails?, id: String) {
        feedbackUseCase.getValues(review, id)

        rating.value = feedbackUseCase.rating
        isAnonymous.value = feedbackUseCase.isAnonymous
        message.value = feedbackUseCase.message
    }

    // Изменение анонимности отзыва
    fun changeAnonymous() {
        feedbackUseCase.changeAnonymous()
        isAnonymous.value = feedbackUseCase.isAnonymous
    }

    // Сохранение отзыва
    fun saveFeedback(descriptionVm: MovieDescriptionViewModel) {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                feedbackUseCase.saveFeedback(
                    descriptionVm,
                    ReviewBody(message.value, rating.value, isAnonymous.value)
                )
            } catch (e: Exception) {
                return@launch
            }
        }
    }

    // Отмена редактирования отзыва
    fun cancel() {
        feedbackUseCase.cancel()

        rating.value = feedbackUseCase.rating
        isAnonymous.value = feedbackUseCase.isAnonymous
        message.value = feedbackUseCase.message
        isFilled.value = false
    }

    // Проверка на заполнение
    fun checkFilling() {
        isFilled.value = feedbackUseCase.checkFilling(this)
    }
}