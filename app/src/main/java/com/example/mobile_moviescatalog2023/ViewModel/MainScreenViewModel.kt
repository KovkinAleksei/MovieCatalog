package com.example.mobile_moviescatalog2023.ViewModel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.mobile_moviescatalog2023.Domain.Movie
import com.example.mobile_moviescatalog2023.Repository.Movies.MoviesResponse
import com.example.mobile_moviescatalog2023.Repository.RetrofitImplementation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainScreenViewModel: ViewModel() {

    // Получение списка фильмов
    fun getMovies(moviesList: MutableState<List<Movie>?>) {
        val movieRetrofit = RetrofitImplementation()
        val api = movieRetrofit.moviesApiImplementation()
        var movieResponse: MoviesResponse? = null

        CoroutineScope(Dispatchers.IO).launch {
            val response = api.getMovies(token = AuthorizationToken.token)
            movieResponse = response

            if (movieResponse != null) {
                moviesList.value = movieResponse!!.movies
            }
        }
    }

    // Подсчёт оценки фильма
    fun getRating(movie: Movie): String {
        val avg = movie.reviews.sumOf { it -> it.rating } / movie.reviews.size.toFloat()

        return String.format("%.1f", avg)
    }
}