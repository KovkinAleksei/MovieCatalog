package com.example.mobile_moviescatalog2023.View

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FavouritesScreen(navController: NavController) {
    Scaffold(
        bottomBar = {
            BottomNavBar(navController)
        }) {
        Text(
            text = "Экран с любимыми фильмами пользователя"
        )
    }

}