package com.example.mobile_moviescatalog2023.ViewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mobile_moviescatalog2023.Domain.MovieDetailsUseCase
import com.example.mobile_moviescatalog2023.Repository.MovieDetails.ReviewDetails
import com.example.mobile_moviescatalog2023.Repository.Movies.Genre
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class MovieDescriptionViewModel : ViewModel() {
    val moviePoster = mutableStateOf("")
    val movieRating = mutableStateOf("")
    val movieName = mutableStateOf("")
    val movieId = mutableStateOf("")
    val description = mutableStateOf("")
    val genres: MutableState<List<Genre>?> = mutableStateOf(null)
    val year = mutableStateOf(0)
    val country = mutableStateOf("")
    val slogan = mutableStateOf("")
    val director = mutableStateOf("")
    val budget = mutableStateOf("")
    val fees = mutableStateOf("")
    val ageLimit = mutableStateOf("")
    val time = mutableStateOf("")
    val reviews: MutableState<List<ReviewDetails>?> = mutableStateOf(null)
    val isInitialized = mutableStateOf(false)
    var isFavorite = mutableStateOf(false)
    var addButtonIsHidden = mutableStateOf(false)
    val isShowingOptions = mutableStateOf(false)
    val isEditingFeedback = mutableStateOf(false)
    var myReview: ReviewDetails? = null

    private val movieDetailsUseCase = MovieDetailsUseCase()

    // Заполнение экрана значениями
    private fun fillValues() {
        movieName.value = movieDetailsUseCase.movieName
        moviePoster.value = movieDetailsUseCase.moviePoster
        movieId.value = movieDetailsUseCase.movieId
        movieRating.value = movieDetailsUseCase.movieRating
        description.value = movieDetailsUseCase.description
        genres.value = movieDetailsUseCase.genres
        year.value = movieDetailsUseCase.year
        country.value = movieDetailsUseCase.country
        slogan.value = movieDetailsUseCase.slogan
        director.value = movieDetailsUseCase.director
        budget.value = movieDetailsUseCase.budget
        fees.value = movieDetailsUseCase.fees
        ageLimit.value = movieDetailsUseCase.ageLimit
        time.value = movieDetailsUseCase.time
        reviews.value = movieDetailsUseCase.reviews
        isFavorite.value = movieDetailsUseCase.isFavorite

        myReview = movieDetailsUseCase.myReview
        addButtonIsHidden.value = false

        isInitialized.value = true
    }

    // Проверка принадлежности отзыва
    fun isOursFeedback(review: ReviewDetails?): Boolean {
        if (movieDetailsUseCase.isOursFeedback(review)) {
            addButtonIsHidden.value = true
            myReview = review
        }

        return movieDetailsUseCase.isOursFeedback(review)
    }

    // Получение подробной информации о фильме
    fun getMovieDetails(
        id: String?,
        isLoaded: MutableState<Boolean>,
        isConnected: MutableState<Boolean>
    ) {
        isInitialized.value = true

        CoroutineScope(Dispatchers.Default).launch {
            try {
                movieDetailsUseCase.getMovieDetails(id)
            } catch (e: UnknownHostException) {
                isConnected.value = false
                return@launch
            }

            fillValues()

            isConnected.value = true
            isLoaded.value = true
        }
    }

    // Добавление фильма в любимые
    fun addToFavourites() {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                movieDetailsUseCase.addToFavourites()
            } catch (e: UnknownHostException) {
                return@launch
            }

            isFavorite.value = true
        }
    }

    // Удаление фильма из списка любимых
    fun deleteFromFavourites() {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                movieDetailsUseCase.deleteFromFavourites()
            } catch (e: UnknownHostException) {
                return@launch
            }

            isFavorite.value = false
        }
    }

    // Удаление отзыва
    fun deleteReview() {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                movieDetailsUseCase.deleteReview()
            } catch (e: UnknownHostException) {
                return@launch
            }

            myReview = movieDetailsUseCase.myReview
            addButtonIsHidden.value = false
            updateFeedback()
        }
    }

    // Раскрытие действий над отзывом
    fun openOptions() {
        isShowingOptions.value = !isShowingOptions.value
    }

    // Редактирование отзыва
    fun editFeedback() {
        isEditingFeedback.value = true
    }

    // Обновление отзыва
    fun updateFeedback() {
        isShowingOptions.value = false
        isEditingFeedback.value = false
        isInitialized.value = false
    }

    // Отмена написания отзыва
    fun closeFeedbackDialog() {
        isShowingOptions.value = false
        isEditingFeedback.value = false
    }
}