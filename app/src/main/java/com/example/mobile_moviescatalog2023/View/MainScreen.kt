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
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.mobile_moviescatalog2023.Domain.Movie
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.Repository.Movies.MoviesResponse
import com.example.mobile_moviescatalog2023.Repository.RetrofitImplementation
import com.example.mobile_moviescatalog2023.ViewModel.AuthorizationToken
import com.example.mobile_moviescatalog2023.ViewModel.MainScreenViewModel
import com.example.mobile_moviescatalog2023.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Главный экран
@Composable
fun MainScreen() {
    val viewModel: MainScreenViewModel = viewModel()
    val moviesResponse = remember{ mutableStateOf<List<Movie>?>(null) }

    if (moviesResponse.value == null)
        viewModel.getMovies(moviesResponse)

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
                    movie -> MovieElement(viewModel, movie)
            }
        }
    }
}

// Оценка фильма по всем отзывам
@Composable
fun Rating(vm: MainScreenViewModel, movie: Movie) {
    val rate = vm.getRating(movie)
    val rateNum = rate.replace(',', '.').toFloat()

    Box(
        modifier = Modifier
            .padding(2.dp)
            .clip(shape = RoundedCornerShape(4.dp))
            .background(
                when {
                    rateNum >= 9 -> darkGreen
                    rateNum >= 8 -> lightGreen
                    rateNum >= 6 -> yellow
                    rateNum >= 4 -> orange
                    rateNum >= 3 -> fire
                    else -> darkRed
                }
            )
    ){
        Text(
            modifier = Modifier
                .padding(4.dp, 0.dp),
            text = rate,
            style = TextStyle (
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            ),
            textAlign = TextAlign.Center
        )
    }
}

// Элемент списка фильмов
@Composable
fun MovieElement(vm: MainScreenViewModel, movie: Movie) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 16.dp, 16.dp)
            .background(DarkGray700)
    ) {
        Row {
            Box {
                AsyncImage(
                    model = movie.poster,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(4.dp))
                )

                Rating(vm, movie)
            }
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

// Подпись жанра фильма
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
        text = stringResource(id = R.string.catalogue),
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