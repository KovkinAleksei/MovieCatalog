package com.example.mobile_moviescatalog2023.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mobile_moviescatalog2023.ui.theme.*

@Composable
fun BottomNavBar(
    navController: NavController
) {
    val itemsList = listOf(
        BottomNavItem.MainScreen,
        BottomNavItem.FavouritesScreen,
        BottomNavItem.ProfileScreen
    )

    BottomNavigation(
        backgroundColor = Gray16,
        modifier = Modifier
            .height(60.dp)
    ) {
        val currentRoute = navController.currentBackStackEntry?.destination?.route

        itemsList.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (item.route != currentRoute) {
                        navController.navigate(item.route) {
                            popUpTo(mainScreen)
                        }
                    }
                          },
                icon = {
                    Image(
                        painter = painterResource(id = if (currentRoute == item.route) item.accentIcon else item.defaultIcon),
                        contentDescription = null
                    )
                }
            )
        }
    }
}