package com.example.mobile_moviescatalog2023.Repository.MovieDetails

import com.example.mobile_moviescatalog2023.Repository.RetrofitImplementation
import com.example.mobile_moviescatalog2023.ViewModel.AuthorizationToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieDetailsRequest {
    // Запрос для получения описания фильма
    suspend fun getMovie(id: String?): MovieDetailsResponse {
        val retrofit = RetrofitImplementation()
        val api = retrofit.getMovieDetailsImplementation()

        return withContext(Dispatchers.Default) {
            api.getMovies(id = (id ?: ""), token = "Bearer ${AuthorizationToken.token}")
        }
    }
}