package com.example.mobile_moviescatalog2023.ViewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mobile_moviescatalog2023.Domain.Movie
import com.example.mobile_moviescatalog2023.Repository.MovieDetails.ReviewDetails
import com.example.mobile_moviescatalog2023.Repository.RetrofitImplementation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavouriteScreenViewModel: ViewModel() {

    var favoriteMoviesList: List<Movie>? = null
    val isLoaded = mutableStateOf(false)
    var ratings: MutableState<List<Float>> = mutableStateOf(listOf())
    private var userId = ""

    init {
        getFavoriteMovies()
    }

    // Получение списка любимых фильмов
    private fun getFavoriteMovies() {
        val retrofit = RetrofitImplementation()
        val api = retrofit.getFavouriteMoviesImplementation()

        CoroutineScope(Dispatchers.Default).launch {
            val response = api.getFavoriteMovies(token = "Bearer ${AuthorizationToken.token}")
            favoriteMoviesList = response.movies

            CoroutineScope(Dispatchers.Default).launch {
                userId = getProfileId()
                ratings.value = getRatings()

                isLoaded.value = true
            }
        }
    }

    // Получение списка оценок фильмов
    private suspend fun getRatings() : List<Float> {
        val rating = favoriteMoviesList?.map { it ->
            getReviews(it.id)?.find { it ->
                (it.author?.userId ?: "") == userId
            }?.rating ?: -1f
        }

        return withContext(Dispatchers.Default) {
            rating ?: listOf()
        }
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
}