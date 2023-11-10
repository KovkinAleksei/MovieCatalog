@file:OptIn(ExperimentalLayoutApi::class)

package com.example.mobile_moviescatalog2023.View

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mobile_moviescatalog2023.Domain.Movie
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.ViewModel.MainScreenViewModel
import com.example.mobile_moviescatalog2023.ui.theme.*
import kotlinx.coroutines.delay

// Главный экран
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavController) {
    val viewModel: MainScreenViewModel = viewModel()
    val moviesResponse = rememberSaveable { mutableStateOf<List<Movie>?>(null) }
    val listState = rememberLazyListState()
    val isLoaded = rememberSaveable {mutableStateOf(false)}
    val isLoading = rememberSaveable {mutableStateOf(true)}

    if (viewModel.isInitialized == false)
        viewModel.getMovies(moviesResponse, isLoaded, isLoading)

    if (isLoaded.value){
        Scaffold (
            bottomBar = {
                BottomNavBar(navController)
            }
        ){
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    FilmsPager()
                    Catalogue()
                }
                items(items = moviesResponse.value!!) { movie ->
                    MovieElement(viewModel, movie, navController)
                }
                item {
                    if (isLoading.value) {
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CircularProgressIndicator(
                                color = AccentColor,
                                strokeWidth = 2.dp,
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.Center)
                            )
                        }
                        Box(modifier = Modifier.height(20.dp))
                    }
                    Box(modifier = Modifier.height(54.dp))
                }
            }
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                val lastVisibleItem = visibleItems.lastOrNull()

                if (lastVisibleItem != null && lastVisibleItem.index == listState.layoutInfo.totalItemsCount - 1 && !isLoading.value) {
                    viewModel.getMovies(moviesResponse, isLoaded, isLoading)
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
fun MovieElement(vm: MainScreenViewModel, movie: Movie, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 16.dp, 16.dp)
            .background(DarkGray700)
            .clickable(
                enabled = true,
                onClick = {
                    navController.navigate("${descriptionScreen}/${movie.id}")
                }
            )
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
            Box{
                Column(
                    modifier = Modifier
                        .padding(10.dp, 0.dp, 45.dp, 0.dp)
                ) {
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

                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 0.dp, 16.dp, 0.dp)
                    ) {
                        movie.genres.forEach {
                                it -> GenreLabel(genre = it.name)
                        }
                    }
                }

                val rating = vm.movieRatings[movie.id]

                if (rating != null && rating != -1f) {
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .align(Alignment.TopEnd)
                    ) {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(darkGreen)
                                .background(
                                    when {
                                        rating >= 9 -> darkGreen
                                        rating >= 8 -> lightGreen
                                        rating >= 6 -> yellow
                                        rating >= 4 -> orange
                                        rating >= 3 -> fire
                                        else -> darkRed
                                    }
                                )
                                .padding(8.dp, 2.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.comment_star),
                                contentDescription = null,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                            )

                            Text(
                                text = rating.toInt().toString(),
                                style = TextStyle(
                                    fontSize = 17.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(4.dp, 0.dp, 0.dp, 0.dp)
                            )
                        }
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
            .clip(shape = RoundedCornerShape(6.dp))
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
    val medialList = listOf(R.drawable.media1, R.drawable.media2, R.drawable.media3, R.drawable.media4)
    val pagerState = rememberPagerState()

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            pagerState.animateScrollToPage(pagerState.currentPage + 1 % 4)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(0.7245f),
                pageCount = 4

            ) { page ->
                Box(
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
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Row(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(10.dp))
                        .background(transparent)
                        .padding(4.dp)
                ) {
                    repeat(4) { iteration ->
                        Image(
                            painter = if (pagerState.currentPage == iteration)
                                painterResource(id = R.drawable.filled_dot)
                            else
                                painterResource(id = R.drawable.hollow_dot),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(4.dp, 2.dp)
                                .size(8.dp)
                        )
                    }
                }
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