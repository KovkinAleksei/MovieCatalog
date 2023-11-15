package com.example.mobile_moviescatalog2023.Repository.Movies

import com.example.mobile_moviescatalog2023.Repository.RetrofitImplementation
import com.example.mobile_moviescatalog2023.ViewModel.AuthorizationToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRequest {
    suspend fun getMovies(pageId: Int): MoviesResponse {
        val movieRetrofit = RetrofitImplementation()
        val api = movieRetrofit.moviesApiImplementation()

        return withContext(Dispatchers.Default) {
            api.getMovies(id = pageId, token = AuthorizationToken.token)
        }
    }
}