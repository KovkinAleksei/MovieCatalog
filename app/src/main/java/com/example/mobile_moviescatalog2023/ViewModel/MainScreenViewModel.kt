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

    private var pagesAmount: Int = 1
    private var pageId: Int = 1
    var isInitialized = false
    var isLoading = false

    // Получение списка фильмов
    fun getMovies(moviesList: MutableState<List<Movie>?>) {
        if (pageId > pagesAmount)
            return

        isLoading = true
        isInitialized = true
        val movieRetrofit = RetrofitImplementation()
        val api = movieRetrofit.moviesApiImplementation()
        var movieResponse: MoviesResponse? = null

        CoroutineScope(Dispatchers.IO).launch {
            val response = api.getMovies(id = pageId, token = AuthorizationToken.token)
            movieResponse = response

            if (movieResponse != null) {
                moviesList.value =
                    if (moviesList.value != null)
                        moviesList.value?.plus(movieResponse!!.movies)
                    else
                        movieResponse!!.movies

                pagesAmount = movieResponse!!.pageInfo.pageCount
                pageId++
            }

            isLoading = false
        }
    }

    // Подсчёт оценки фильма
    fun getRating(movie: Movie): String {
        val avg = movie.reviews.sumOf { it -> it.rating } / movie.reviews.size.toFloat()

        return String.format("%.1f", avg)
    }
}