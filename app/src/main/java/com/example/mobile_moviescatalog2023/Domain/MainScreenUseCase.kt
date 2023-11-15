package com.example.mobile_moviescatalog2023.Domain

import androidx.compose.runtime.MutableState
import com.example.mobile_moviescatalog2023.Repository.MovieDetails.MovieDetailsRequest
import com.example.mobile_moviescatalog2023.Repository.MovieDetails.ReviewDetails
import com.example.mobile_moviescatalog2023.Repository.Movies.Movie
import com.example.mobile_moviescatalog2023.Repository.Movies.MoviesRequest
import com.example.mobile_moviescatalog2023.Repository.Movies.MoviesResponse
import com.example.mobile_moviescatalog2023.Repository.UserProfile.ProfileRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

class MainScreenUseCase {
    var movieRatings: MutableMap<String, Float> = mutableMapOf("" to -1f)

    private var pagesAmount: Int = 1
    private var pageId: Int = 1
    private var userId = ""
    private var movies: List<Movie> = listOf()
    private val movieRequest = MoviesRequest()
    private val profileRequest = ProfileRequest()
    private val movieDetailsRequest = MovieDetailsRequest()

    // Перезагрузка фильмов
    fun reload() {
        pageId = 1
        movies = listOf()
    }

    // Получение списка фильмов
    suspend fun getMovies(moviesList: MutableState<List<Movie>?>) {
        if (pageId > pagesAmount)
            return

        val movieResponse: MoviesResponse

        try {
            movieResponse = movieRequest.getMovies(pageId)
        } catch (e: UnknownHostException) {
            return
        }

        movies = movies.plus(movieResponse.movies)
        userId = getProfileId()

        movieResponse.movies.forEach { getUsersRating(it.id) }
        pagesAmount = movieResponse.pageInfo.pageCount
        pageId++

        moviesList.value = movies
    }

    // Получение списка оценок фильмов
    private suspend fun getUsersRating(movieId: String) {
        val userRate = getReviews(movieId)?.find { it -> (it.author?.userId ?: "") == userId }?.rating ?: -1f
        movieRatings[movieId] = userRate
    }

    // Получение списка отзывов к фильму
    private suspend fun getReviews(movieId: String): List<ReviewDetails>? {
        return withContext(Dispatchers.Default) {
            movieDetailsRequest.getMovie(movieId).reviews
        }
    }

    // Получение данных профиля из Api
    private suspend fun getProfileId(): String {
        return withContext(Dispatchers.Default) {
            profileRequest.getProfile().id
        }
    }

    // Подсчёт оценки фильма
    fun getRating(movie: Movie): String {
        val avg = movie.reviews.sumOf { it.rating } / movie.reviews.size.toFloat()

        return String.format("%.1f", avg)
    }
}