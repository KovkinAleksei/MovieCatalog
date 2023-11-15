package com.example.mobile_moviescatalog2023.Domain

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.mobile_moviescatalog2023.Repository.FavouriteMovies.FavouriteMovieRequest
import com.example.mobile_moviescatalog2023.Repository.MovieDetails.MovieDetailsRequest
import com.example.mobile_moviescatalog2023.Repository.MovieDetails.ReviewDetails
import com.example.mobile_moviescatalog2023.Repository.Movies.Movie
import com.example.mobile_moviescatalog2023.Repository.UserProfile.ProfileRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavouriteUseCase {
    var favoriteMoviesList: List<Movie>? = null
    val ratings: MutableState<List<Float>> = mutableStateOf(listOf())
    var isLoaded = mutableStateOf(false)
    var userId = ""

    private val favouriteMovieRequest = FavouriteMovieRequest()
    private val movieDetailsRequest = MovieDetailsRequest()
    private val profileRequest = ProfileRequest()

    // Получение списка любимых фильмов
    suspend fun getFavoriteMovies() {
        favoriteMoviesList = favouriteMovieRequest.getFavouriteMovies().movies
        userId = getProfileId()
        ratings.value = getRatings()
        isLoaded.value = true
    }

    // Получение списка оценок фильмов
    private suspend fun getRatings(): List<Float> {
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
}