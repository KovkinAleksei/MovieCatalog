package com.example.mobile_moviescatalog2023

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import com.example.mobile_moviescatalog2023.ui.theme.MobileMoviesCatalog2023Theme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobile_moviescatalog2023.View.RegistrationOrLoginScreen
import com.example.mobile_moviescatalog2023.View.RegistrationScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            MobileMoviesCatalog2023Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "screen_1"
                    ) {
                        composable("screen_1") {
                            RegistrationOrLoginScreen {
                                navController.navigate("screen_2")
                            }
                        }
                        composable("screen_2") {
                            RegistrationScreen{
                                navController.navigate("screen_1")
                            }
                        }
                    }
                }
            }
        }
    }
}