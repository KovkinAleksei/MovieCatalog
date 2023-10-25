package com.example.mobile_moviescatalog2023.View

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.Domain.Genre
import com.example.mobile_moviescatalog2023.Domain.Movie
import com.example.mobile_moviescatalog2023.Domain.Review
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.Repository.Movies.MoviesResponse
import com.example.mobile_moviescatalog2023.Repository.RetrofitImplementation
import com.example.mobile_moviescatalog2023.ViewModel.AuthorizationToken
import com.example.mobile_moviescatalog2023.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
    val moviesResponse = remember{ mutableStateOf<List<Movie>?>(null) }

    if (moviesResponse.value == null)
        getMovies(moviesResponse)

    if (moviesResponse.value != null){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                FilmsPager()
                Catalogue()
            }
            items(items = moviesResponse.value!!) {
                    movie -> MovieElement(movie)
            }
        }
    }

}

@Composable
fun MovieElement(movie: Movie) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 16.dp, 16.dp),
        backgroundColor = DarkGray700
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.media3),
                contentDescription = null,
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(10.dp))
                    .width(100.dp)
            )
            Column(
                modifier = Modifier
                    .padding(10.dp, 0.dp)
            ){
                Column {
                    Text(
                        text = movie.name,
                        style = TextStyle(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    )
                    Text(
                        modifier = Modifier
                            .padding(0.dp, 8.dp),
                        text = "${movie.year} · ${movie.country}",
                        style = TextStyle(
                            color = Gray90,
                            fontSize = 14.sp
                        )
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 16.dp, 0.dp)
                ){
                    if (movie.genres.size >= 1)
                        GenreLabel(movie.genres[0].name)
                    if (movie.genres.size >=2)
                        GenreLabel(movie.genres[1].name)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 16.dp, 0.dp)
                ){
                    if (movie.genres.size >=3)
                        GenreLabel(movie.genres[2].name)
                    if (movie.genres.size >=4)
                        GenreLabel(movie.genres[3].name)

                    if (movie.genres.size > 4){
                        Text(
                            text = "...",
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 14.sp
                            ),
                            modifier = Modifier
                                .padding(4.dp, 8.dp, 16.dp, 0.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GenreLabel(genre: String) {
    Box (
        modifier = Modifier
            .padding(2.dp)
            .clip(shape = RoundedCornerShape(4.dp))
            .background(Gray40)
    ) {
        Text(
            text= genre,
            style = TextStyle(
                fontSize = 14.sp,
                color = Color.White
            ),
            modifier = Modifier
                .padding(8.dp, 2.dp)
                .height(18.dp)
        )
    }
}
// Карусель постеров фильмов
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilmsPager() {
    val medialList = listOf( R.drawable.media1, R.drawable.media2, R.drawable.media3, R.drawable.media4)

    val pagerState = rememberPagerState()
    var currentPage = remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            currentPage.value = (currentPage.value + 1) % 4
            pagerState.animateScrollToPage((currentPage.value))
        }
    }

    Column (
        modifier = Modifier
            .fillMaxWidth()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(0.7245f),
            pageCount = 4

        ) {
            page -> Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = medialList[page]),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
    }
}

// Надпсиь "Каталог"
@Composable
fun Catalogue() {
    Text(
        text = "Каталог",
        style = TextStyle(
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        ),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )
}

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