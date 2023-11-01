package com.example.mobile_moviescatalog2023.View

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.R
import androidx.navigation.NavController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FavouritesScreen(navController: NavController) {
    Scaffold(
        bottomBar = {
            BottomNavBar(navController)
        }) {
        Column{
            FavouritesHeader()
            Spacer(modifier = Modifier.height(100.dp))
            NoFavouriteFilms()
            ChooseFaviuriteFilm()
        }
    }
}

@Composable
fun FavouritesHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp, 16.dp, 0.dp)
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