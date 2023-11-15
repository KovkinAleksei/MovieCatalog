@file:OptIn(ExperimentalSerializationApi::class)

package com.example.mobile_moviescatalog2023.Repository

import com.example.mobile_moviescatalog2023.Repository.FavouriteMovies.AddFavouriteMovieApi
import com.example.mobile_moviescatalog2023.Repository.FavouriteMovies.DeleteFavouriteMovieApi
import com.example.mobile_moviescatalog2023.Repository.FavouriteMovies.GetFavouriteMoviesApi
import com.example.mobile_moviescatalog2023.Repository.Login.LoginApi
import com.example.mobile_moviescatalog2023.Repository.Login.LogoutApi
import com.example.mobile_moviescatalog2023.Repository.MovieDetails.MovieDetailsApi
import com.example.mobile_moviescatalog2023.Repository.Movies.MovieApi
import com.example.mobile_moviescatalog2023.Repository.Registration.RegistrationApi
import com.example.mobile_moviescatalog2023.Repository.Review.AddReviewApi
import com.example.mobile_moviescatalog2023.Repository.Review.DeleteReviewApi
import com.example.mobile_moviescatalog2023.Repository.Review.EditReviewApi
import com.example.mobile_moviescatalog2023.Repository.UserProfile.GetProfileApi
import com.example.mobile_moviescatalog2023.Repository.UserProfile.PutProfileApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create

class RetrofitImplementation {
    private val retrofit = Retrofit.Builder()
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }).build()
        )
        .baseUrl("https://react-midterm.kreosoft.space/api/")
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()

    fun loginApiImplementation(): LoginApi {
        return retrofit.create()
    }

    fun registrationApiImplementation(): RegistrationApi {
        return retrofit.create()
    }

    fun moviesApiImplementation(): MovieApi {
        return retrofit.create()
    }

    fun getProfileImplementation(): GetProfileApi {
        return retrofit.create()
    }

    fun putProfileImplementation(): PutProfileApi {
        return retrofit.create()
    }

    fun logoutImplementation(): LogoutApi {
        return retrofit.create()
    }

    fun getMovieDetailsImplementation(): MovieDetailsApi {
        return retrofit.create()
    }

    fun addFavouriteMovieImplementation(): AddFavouriteMovieApi {
        return retrofit.create()
    }

    fun getFavouriteMoviesImplementation(): GetFavouriteMoviesApi {
        return retrofit.create()
    }

    fun deleteFavouriteMoviesImplementation(): DeleteFavouriteMovieApi {
        return retrofit.create()
    }

    fun addReviewImplementation(): AddReviewApi {
        return retrofit.create()
    }

    fun editReviewImplementation(): EditReviewApi {
        return retrofit.create()
    }

    fun deleteReviewImplementation(): DeleteReviewApi {
        return retrofit.create()
    }
}