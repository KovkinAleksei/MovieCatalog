@file:OptIn(ExperimentalLayoutApi::class)

package com.example.mobile_moviescatalog2023.View

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile_moviescatalog2023.R
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mobile_moviescatalog2023.Repository.Movies.Movie
import com.example.mobile_moviescatalog2023.ViewModel.FavouriteScreenViewModel
import com.example.mobile_moviescatalog2023.ui.theme.*

// Экран с любимыми фильмами пользователя
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FavouritesScreen(navController: NavController) {
    val vm: FavouriteScreenViewModel = viewModel()

    if (vm.isLoaded.value){
        Scaffold(
            bottomBar = {
                BottomNavBar(navController)
            }) {
            Column{
                FavouritesHeader()

                if (vm.favoriteMoviesList?.isEmpty() == true) {
                    EmptyFavouriteScreen()
                }
                else {
                    FilledScreen(vm, navController)
                }
            }
        }
    }
    else {
        LoadingScreen()
    }

}

// Экран с наполненным списком любимых фильмов
@Composable
fun FilledScreen(vm: FavouriteScreenViewModel, navController: NavController) {
    var index = 0

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(0.dp, 0.dp, 0.dp, 70.dp)
    ) {
        while (index < vm.favoriteMoviesList?.size ?: 0) {
            if (index % 3 != 2) {
                DoubleMovieRow(vm, index, navController)
                index += 2
            }
            else {
                SingleMovieRow(vm, index, navController)
                index++
            }
        }
    }
}

// Строка с двумя фильмами
@Composable
fun DoubleMovieRow(vm: FavouriteScreenViewModel, index: Int, navController: NavController) {
    Row(
        modifier = Modifier
            .padding(16.dp, 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(0.dp, 16.dp, 8.dp, 0.dp)
        ) {
            MovieCard(vm.favoriteMoviesList?.get(index), vm, index, navController)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 16.dp, 0.dp, 0.dp)
        ) {
            if (index + 1 < vm.favoriteMoviesList?.size ?: 0) {
                MovieCard(vm.favoriteMoviesList?.get(index +1), vm, index + 1, navController)
            }
        }
    }
}

// Строка с одним фильмом
@Composable
fun SingleMovieRow(vm: FavouriteScreenViewModel, index: Int, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp, 16.dp, 0.dp)
    ) {
        MovieCard(vm.favoriteMoviesList?.get(index), vm, index, navController)
    }
}

// Элемент с фильмом
@Composable
fun MovieCard(movie: Movie?, vm: FavouriteScreenViewModel, index: Int, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = true,
                onClick = {
                    navController.navigate("${descriptionScreen}/${movie?.id ?: ""}")
                }
            )
    ) {
        Box{
            AsyncImage(
                model = movie?.poster,
                contentDescription = null,
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(4.dp))
                    .fillMaxWidth()
                    .height(230.dp),
                contentScale = ContentScale.Crop
            )

            var rateNum = vm.ratings.value[index]

            if (rateNum != -1f)
            {
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
                                    rateNum >= 9 -> darkGreen
                                    rateNum >= 8 -> lightGreen
                                    rateNum >= 6 -> yellow
                                    rateNum >= 4 -> orange
                                    rateNum >= 3 -> fire
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
                            text = rateNum.toInt().toString(),
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

        Text(
            text = movie?.name ?: "",
            style = TextStyle(
                color = Color.White,
                fontSize = 16.sp
            ),
            modifier = Modifier
                .padding(0.dp, 4.dp, 0.dp, 0.dp)
        )
    }
}

@Composable
fun EmptyFavouriteScreen() {
    Spacer(modifier = Modifier.height(100.dp))
    NoFavouriteFilms()
    ChooseFaviuriteFilm()
}

@Composable
fun FavouritesHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp, 16.dp, 16.dp)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = stringResource(id = R.string.favourite),
            style = TextStyle(
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        )
    }
}

@Composable
fun NoFavouriteFilms() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(56.dp, 16.dp, 56.dp, 0.dp)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = stringResource(id = R.string.no_films),
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
fun ChooseFaviuriteFilm() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 4.dp, 16.dp, 0.dp)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = stringResource(id = R.string.choose_favourite),
            style = TextStyle(
                fontSize = 17.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        )
    }
}