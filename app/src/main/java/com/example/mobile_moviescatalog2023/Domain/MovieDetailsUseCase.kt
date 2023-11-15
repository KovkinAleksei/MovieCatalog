package com.example.mobile_moviescatalog2023.Domain

import com.example.mobile_moviescatalog2023.Repository.FavouriteMovies.FavouriteMovieRequest
import com.example.mobile_moviescatalog2023.Repository.MovieDetails.MovieDetailsRequest
import com.example.mobile_moviescatalog2023.Repository.MovieDetails.MovieDetailsResponse
import com.example.mobile_moviescatalog2023.Repository.MovieDetails.ReviewDetails
import com.example.mobile_moviescatalog2023.Repository.Movies.Genre
import com.example.mobile_moviescatalog2023.Repository.Review.ReviewRequest
import com.example.mobile_moviescatalog2023.Repository.UserProfile.ProfileRequest
import java.text.NumberFormat
import java.util.*

class MovieDetailsUseCase {
    var movieName = ""
    var moviePoster = ""
    var movieId = ""
    var movieRating = ""
    var description = ""
    var genres: List<Genre>? = null
    var year = -1
    var country = ""
    var slogan = ""
    var director = ""
    var budget = ""
    var fees = ""
    var ageLimit = ""
    var time = ""
    var reviews: List<ReviewDetails>? = null
    var isFavorite = false
    var userId = ""
    var myReview: ReviewDetails? = null

    private val movieDetailsRequest = MovieDetailsRequest()
    private val favouriteMovieRequest = FavouriteMovieRequest()
    private val reviewRequest = ReviewRequest()
    private val profileRequest = ProfileRequest()
    private var movieDetails: MovieDetailsResponse? = null

    // Получение данных о фильме
    private fun getValues() {
        movieName = movieDetails?.name ?: ""
        moviePoster = movieDetails?.poster ?: ""
        movieId = movieDetails?.id ?: ""
        movieRating = getMovieRating(movieDetails?.reviews)
        description = movieDetails?.description ?: ""
        genres = movieDetails?.genres
        year = movieDetails?.year ?: -1
        country = movieDetails?.country ?: ""
        slogan = movieDetails?.tagline ?: ""
        director = movieDetails?.director ?: ""

        budget = if (movieDetails?.budget != null)
            "$${
                NumberFormat.getNumberInstance(Locale.US).format(movieDetails?.budget)
            }".replace(',', ' ')
        else
            ""

        fees = if (movieDetails?.fees != null)
            "$${NumberFormat.getNumberInstance(Locale.US).format(movieDetails?.fees)}".replace(
                ',',
                ' '
            )
        else
            ""

        ageLimit = "${movieDetails?.ageLimit}+"
        time = "${movieDetails?.time} мин."
        reviews = movieDetails?.reviews
        myReview = null
    }

    // Получение подробной информации о фильме
    suspend fun getMovieDetails(id: String?) {
        movieDetails = movieDetailsRequest.getMovie(id)

        getValues()

        checkFavouriteMovie()
        getProfile()
    }

    // Добавление фильма в любимые
    suspend fun addToFavourites() {
        favouriteMovieRequest.addFavouriteMovie(movieDetails)
        isFavorite = true
    }

    // Удаление фильма из списка любимых
    suspend fun deleteFromFavourites() {
        favouriteMovieRequest.deleteMovieFromFavourite(movieDetails)
        isFavorite = false
    }

    // Проверка, находится ли фильм в списке любимых
    private suspend fun checkFavouriteMovie() {
        val response = favouriteMovieRequest.getFavouriteMovies()
        isFavorite = movieDetails?.id in response.movies.map { it.id }
    }

    // Получение данных профиля из Api
    private suspend fun getProfile() {
        val response = profileRequest.getProfile()
        userId = response.id
    }

    // Подсчёт оценки фильма
    private fun getMovieRating(reviews: List<ReviewDetails>?): String {
        if (reviews == null)
            return "0"

        val avg = reviews.sumOf { it.rating.toDouble() } / reviews.size.toFloat()

        return String.format("%.1f", avg)
    }

    // Проверка принадлежности отзыва
    fun isOursFeedback(review: ReviewDetails?): Boolean {
        if ((review?.author?.userId ?: "") == userId) {
            myReview = review
            return true
        }

        return false
    }

    // Удаление отзыва
    suspend fun deleteReview() {
        reviewRequest.deleteReview(movieId, myReview?.id ?: "")
        myReview = null
    }
}