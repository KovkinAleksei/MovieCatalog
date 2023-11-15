package com.example.mobile_moviescatalog2023.ViewModel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.mobile_moviescatalog2023.Domain.MainScreenUseCase
import com.example.mobile_moviescatalog2023.Repository.Movies.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class MainScreenViewModel : ViewModel() {
    var isInitialized = false
    var movieRatings: MutableMap<String, Float> = mutableMapOf("" to -1f)

    private val mainScreenUseCase = MainScreenUseCase()

    // Перезагрузка фильмов
    fun reload() {
        mainScreenUseCase.reload()
    }

    // Получение списка фильмов
    fun getMovies(
        moviesList: MutableState<List<Movie>?>,
        isLoaded: MutableState<Boolean>,
        isLoading: MutableState<Boolean>
    ) {
        isInitialized = true
        isLoading.value = true

        CoroutineScope(Dispatchers.Default).launch {
            try {
                mainScreenUseCase.getMovies(moviesList)
            } catch (e: UnknownHostException) {
                moviesList.value = null
                isLoaded.value = false
                return@launch
            }

            movieRatings = mainScreenUseCase.movieRatings
            isLoaded.value = true
            isLoading.value = false
        }
    }

    // Подсчёт оценки фильма
    fun getRating(movie: Movie): String {
        return mainScreenUseCase.getRating(movie)
    }
}