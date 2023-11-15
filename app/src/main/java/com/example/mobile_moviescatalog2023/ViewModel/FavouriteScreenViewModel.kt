package com.example.mobile_moviescatalog2023.ViewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mobile_moviescatalog2023.Domain.FavouriteUseCase
import com.example.mobile_moviescatalog2023.Repository.Movies.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class FavouriteScreenViewModel : ViewModel() {
    var favoriteMoviesList: List<Movie>? = null
    var isLoaded = mutableStateOf(false)
    var ratings: MutableState<List<Float>> = mutableStateOf(listOf())
    var isLoading = false
    var connectionFailed = false

    private val favouriteUseCase = FavouriteUseCase()

    init {
        getFavoriteMovies()
    }

    // Получение списка любимых фильмов
    private fun getFavoriteMovies() {
        isLoading = true
        ratings = favouriteUseCase.ratings
        isLoaded = favouriteUseCase.isLoaded

        CoroutineScope(Dispatchers.Default).launch {
            try {
                favouriteUseCase.getFavoriteMovies()
            } catch (e: UnknownHostException) {
                connectionFailed = true
                isLoaded.value = true
            }
            favoriteMoviesList = favouriteUseCase.favoriteMoviesList
            isLoading = false
        }
    }
}