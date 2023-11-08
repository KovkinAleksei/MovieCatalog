package com.example.mobile_moviescatalog2023.ViewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mobile_moviescatalog2023.Domain.Genre
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
    var isInitialized = false

    private var movieDetails: MovieDetailsResponse? = null

    // Заполнение экрана значениями
    private fun fillValues() {
        movieName.value = movieDetails?.name ?: ""
        moviePoster.value = movieDetails?.poster ?: ""
        movieRating.value = getMovieRating(movieDetails?.reviews)
        description.value = movieDetails?.description ?: ""
        genres.value = movieDetails?.genres
        year.value = movieDetails?.year ?: -1
        country.value = movieDetails?.country ?: ""
        slogan.value = movieDetails?.tagline ?: ""
        director.value = movieDetails?.director ?: ""
        budget.value = "$${NumberFormat.getNumberInstance(Locale.US).format(movieDetails?.budget)}".replace(',', ' ')
        fees.value = "$${NumberFormat.getNumberInstance(Locale.US).format(movieDetails?.fees)}".replace(',', ' ')
        ageLimit.value = "${movieDetails?.ageLimit}+"
        time.value = "${movieDetails?.time} мин."
        reviews.value = movieDetails?.reviews

        isInitialized = true
    }

    // Получение подробной информации о фильме
    fun getMovieDetails(id: String?, isLoaded: MutableState<Boolean> ) {
        val movieRetrofit = RetrofitImplementation()
        val api = movieRetrofit.getMovieDetailsImplementation()

        CoroutineScope(Dispatchers.IO).launch {
            val response = api.getMovies(id = (id ?: ""), token = AuthorizationToken.token)
            movieDetails = response
            fillValues()
            isLoaded.value = true
        }
    }

    // Подсчёт оценки фильма
    private fun getMovieRating(reviews: List<ReviewDetails>?): String {
        if (reviews == null)
            return "0"

        val avg = reviews.sumOf { it -> it.rating.toDouble() } / reviews.size.toFloat()

        return String.format("%.1f", avg)
    }
}