package com.example.mobile_moviescatalog2023.View

import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.ui.theme.favouritesScreen
import com.example.mobile_moviescatalog2023.ui.theme.mainScreen
import com.example.mobile_moviescatalog2023.ui.theme.profileScreen

sealed class BottomNavItem(
    val defaultIcon: Int,
    val accentIcon: Int,
    val route: String
) {
    object MainScreen : BottomNavItem(
        R.drawable.home_page_default,
        R.drawable.home_page_accent,
        mainScreen
    )

    object FavouritesScreen : BottomNavItem(
        R.drawable.favourites_default,
        R.drawable.favourites_accent,
        favouritesScreen
    )

    object ProfileScreen : BottomNavItem(
        R.drawable.profile_default,
        R.drawable.profile_accent,
        profileScreen
    )
}