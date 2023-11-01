package com.example.mobile_moviescatalog2023.View

import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.ui.theme.favouritesScreen
import com.example.mobile_moviescatalog2023.ui.theme.mainScreen
import com.example.mobile_moviescatalog2023.ui.theme.profileScreen

sealed class BottomNavItem(val title: String, val defaultIcon: Int, val accentIcon: Int, val route: String) {
    object MainScreen: BottomNavItem("Главная", R.drawable.home_page_default, R.drawable.home_page_accent, mainScreen)
    object FavouritesScreen: BottomNavItem("Любимое", R.drawable.favourites_default, R.drawable.favourites_accent, favouritesScreen)
    object ProfileScreen: BottomNavItem("Профиль", R.drawable.profile_default, R.drawable.profile_accent, profileScreen)
}