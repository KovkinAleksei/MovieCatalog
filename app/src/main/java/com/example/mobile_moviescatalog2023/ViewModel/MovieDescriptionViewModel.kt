package com.example.mobile_moviescatalog2023.ViewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mobile_moviescatalog2023.Repository.Movies.Genre
import com.example.mobile_moviescatalog2023.Repository.Movies.Movie
import com.example.mobile_moviescatalog2023.Repository.MovieDetails.MovieDetailsResponse
import com.example.mobile_moviescatalog2023.Repository.MovieDetails.ReviewDetails
import com.example.mobile_moviescatalog2023.Repository.RetrofitImplementation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*

class MovieDescriptionViewModel: ViewModel() {
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
    var isInitialized = mutableStateOf(false)
    var isFavorite = mutableStateOf(false)
    var ourFeedbackIsFound = mutableStateOf(false)
    val isShowingOptions = mutableStateOf(false)
    val isEditingFeedback = mutableStateOf(false)
    var myReview: ReviewDetails? = null

    private var favoriteMoviesList: List<Movie> = listOf()
    private var userId: String = ""
    private var movieDetails: MovieDetailsResponse? = null

    // Заполнение экрана значениями
    private fun fillValues() {
        movieName.value = movieDetails?.name ?: ""
        moviePoster.value = movieDetails?.poster ?: ""
        movieId.value = movieDetails?.id ?: ""
        movieRating.value = getMovieRating(movieDetails?.reviews)
        description.value = movieDetails?.description ?: ""
        genres.value = movieDetails?.genres
        year.value = movieDetails?.year ?: -1
        country.value = movieDetails?.country ?: ""
        slogan.value = movieDetails?.tagline ?: ""
        director.value = movieDetails?.director ?: ""

        budget.value = if (movieDetails?.budget != null)
            "$${NumberFormat.getNumberInstance(Locale.US).format(movieDetails?.budget)}".replace(',', ' ')
        else
            ""

        fees.value = if (movieDetails?.fees != null)
            "$${NumberFormat.getNumberInstance(Locale.US).format(movieDetails?.fees)}".replace(',', ' ')
        else
            ""

        ageLimit.value = "${movieDetails?.ageLimit}+"
        time.value = "${movieDetails?.time} мин."
        reviews.value = movieDetails?.reviews

        isInitialized.value = true
    }

    // Проверка принадлежности отзыва
    fun isOursFeedback(review: ReviewDetails?) : Boolean {
        if (review?.author?.userId ?: "" == userId) {
            ourFeedbackIsFound.value = true
            myReview = review
            return true
        }

        return false
    }

    // Получение данных профиля из Api
    private suspend fun getProfile() {
        val profileRetrofit = RetrofitImplementation()
        val api = profileRetrofit.getProfileImplementation()

        val response = api.getProfile(token = "Bearer ${AuthorizationToken.token}")

        userId = response.id
    }

    // Получение подробной информации о фильме
    fun getMovieDetails(id: String?, isLoaded: MutableState<Boolean> ) {
        val retrofit = RetrofitImplementation()
        val api = retrofit.getMovieDetailsImplementation()

        CoroutineScope(Dispatchers.IO).launch {
            val response = api.getMovies(id = (id ?: ""), token = "Bearer ${AuthorizationToken.token}")
            movieDetails = response

            fillValues()
            getFavoriteMovies(isLoaded)
            getProfile()
        }
    }

    // Добавление фильма в любимые
    fun addToFavourites() {
        val retrofit = RetrofitImplementation()
        val api = retrofit.addFavouriteMovieImplementation()

        CoroutineScope(Dispatchers.Default).launch {
            api.addFavouriteMovie(id = movieDetails?.id ?: "", token = "Bearer ${AuthorizationToken.token}")
        }

        isFavorite.value = true
    }

    // Получение списка любимых фильмов
    private fun getFavoriteMovies(isLoaded: MutableState<Boolean>) {
        val retrofit = RetrofitImplementation()
        val api = retrofit.getFavouriteMoviesImplementation()

        CoroutineScope(Dispatchers.Default).launch {
            val response = api.getFavoriteMovies(token = "Bearer ${AuthorizationToken.token}")
            favoriteMoviesList = response.movies

            isFavorite.value = movieDetails?.id in response.movies.map { it -> it.id }
            isLoaded.value = true
        }
    }

    // Удаление фильма из списка любимых
    fun deleteFromFavourites() {
        val retrofit = RetrofitImplementation()
        val api = retrofit.deleteFavouriteMoviesImplementation()

        CoroutineScope(Dispatchers.Default).launch {
            api.deleteFavoriteMovies(id = movieDetails?.id ?: "", token = "Bearer ${AuthorizationToken.token}")
        }

        isFavorite.value = false
    }

    // Подсчёт оценки фильма
    private fun getMovieRating(reviews: List<ReviewDetails>?): String {
        if (reviews == null)
            return "0"

        val avg = reviews.sumOf { it -> it.rating.toDouble() } / reviews.size.toFloat()

        return String.format("%.1f", avg)
    }

    // Удаление отзыва
    fun deleteReview() {
        val retrofit = RetrofitImplementation()
        val api = retrofit.deleteReviewImplementation()

        CoroutineScope(Dispatchers.Default).launch {
            api.deleteReview(
                movieId = movieId.value,
                reviewId = myReview?.id ?: "",
                token = "Bearer ${AuthorizationToken.token}"
            )

            myReview = null
            ourFeedbackIsFound.value = false
        }

        updateFeedback()
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