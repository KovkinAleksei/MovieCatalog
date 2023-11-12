package com.example.mobile_moviescatalog2023.ViewModel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.mobile_moviescatalog2023.Repository.Movies.Movie
import com.example.mobile_moviescatalog2023.Repository.MovieDetails.ReviewDetails
import com.example.mobile_moviescatalog2023.Repository.Movies.MoviesResponse
import com.example.mobile_moviescatalog2023.Repository.RetrofitImplementation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainScreenViewModel: ViewModel() {

    private var pagesAmount: Int = 1
    private var pageId: Int = 1
    var isInitialized = false

    private var userId = ""
    private var movies: List<Movie> = listOf()

    var movieRatings: MutableMap<String, Float> = mutableMapOf("" to -1f)

    // Получение списка фильмов
    fun getMovies(moviesList: MutableState<List<Movie>?>, isLoaded: MutableState<Boolean>, isLoading: MutableState<Boolean>) {
        if (pageId > pagesAmount)
            return

        isLoading.value = true
        isInitialized = true

        val movieRetrofit = RetrofitImplementation()
        val api = movieRetrofit.moviesApiImplementation()
        var movieResponse: MoviesResponse? = null

        CoroutineScope(Dispatchers.IO).launch {
            val response = api.getMovies(id = pageId, token = AuthorizationToken.token)
            movieResponse = response

            if (movieResponse != null) {
                movies = movies.plus(movieResponse!!.movies)
                userId = getProfileId()

                movieResponse!!.movies.forEach {
                    it -> getUsersRating(it.id)
                }

                pagesAmount = movieResponse!!.pageInfo.pageCount
                pageId++
            }

            isLoaded.value = true
            isLoading.value = false
            moviesList.value = movies
        }
    }

    // Получение списка оценок фильмов
     private suspend fun getUsersRating(movieId: String) {
        var userRate: Float = -1f

        userRate = getReviews(movieId)?.find { it ->
            (it.author?.userId ?: "") == userId
        }?.rating ?: -1f

        movieRatings[movieId] = userRate
    }

    // Получение списка отзывов к фильму
    private suspend fun getReviews(movieId: String): List<ReviewDetails>? {
        val retrofit = RetrofitImplementation()
        val api = retrofit.getMovieDetailsImplementation()

        return withContext(Dispatchers.Default) {
            api.getMovies(id = movieId, token = "Bearer ${AuthorizationToken.token}")!!.reviews
        }
    }

    // Получение данных профиля из Api
    private suspend fun getProfileId(): String {
        val profileRetrofit = RetrofitImplementation()
        val api = profileRetrofit.getProfileImplementation()

        return withContext(Dispatchers.Default) {
            val response = api.getProfile(token = "Bearer ${AuthorizationToken.token}")
            response.id
        }
    }

    // Подсчёт оценки фильма
    fun getRating(movie: Movie): String {
        val avg = movie.reviews.sumOf { it -> it.rating } / movie.reviews.size.toFloat()

        return String.format("%.1f", avg)
    }
}