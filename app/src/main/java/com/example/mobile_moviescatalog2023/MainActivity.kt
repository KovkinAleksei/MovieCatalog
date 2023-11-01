package com.example.mobile_moviescatalog2023

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobile_moviescatalog2023.View.*
import com.example.mobile_moviescatalog2023.ui.theme.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            MobileMoviesCatalog2023Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = regOrLogScreen
                    ) {
                        // Экран выбора регистрации или авторизации
                        composable(regOrLogScreen){
                            RegistrationOrLoginScreen (
                                onRegButtonClick = {
                                    navController.navigate(registrationScreen)
                                },

                                onLoginButtonClick = {
                                    navController.navigate(loginScreen)}
                                )
                        }

                        // Первый экран регистрации
                        composable(registrationScreen) {
                            RegistrationScreen(
                                onBackButtonClick = {
                                    navController.navigate(regOrLogScreen) {
                                        popUpTo(regOrLogScreen) {
                                            inclusive = true
                                        }
                                    }
                                },

                                onLoginClick = {
                                    navController.navigate(loginScreen) {
                                        popUpTo(regOrLogScreen)
                                    }
                                },

                                onContinueButtonClick = {
                                    navController.navigate(registrationPasswordScreen)
                                }
                            )
                        }

                        // Экран авторизации
                        composable("login_screen") {
                            LoginScreen(
                                onBackButtonClick = {
                                    navController.navigate(regOrLogScreen) {
                                        popUpTo(regOrLogScreen) {
                                            inclusive = true
                                        }
                                    }
                                },

                                onRegistrationClick = {
                                    navController.navigate(registrationScreen) {
                                        popUpTo(regOrLogScreen)
                                    }
                                },

                                onLoginButtonClick = {
                                    navController.navigate(mainScreen) {
                                        popUpTo(regOrLogScreen) {
                                            inclusive = true
                                        }
                                    }
                                }
                            )
                        }

                        // Второй экран регистрации
                        composable(registrationPasswordScreen) {
                            RegistrationPasswordScreen(
                                onBackButtonClick = {
                                    navController.popBackStack()
                                },

                                onSignInClick = {
                                    navController.navigate(loginScreen) {
                                        popUpTo(regOrLogScreen)
                                    }
                                },

                                onRegistrationButtonClick = {
                                    navController.navigate(mainScreen) {
                                        popUpTo(regOrLogScreen) {
                                            inclusive = true
                                        }
                                    }
                                }
                            )
                        }

                        // Главный экран
                        composable(mainScreen) {
                            MainScreen(navController)
                        }

                        // Экран с профилем пользователя
                        composable ("profileScreen") {
                            ProfileScreen(
                                navController = navController,
                                onExitButtonClick = {
                                    navController.navigate(regOrLogScreen) {
                                        popUpTo(regOrLogScreen) {
                                            inclusive = true
                                        }
                                    }
                                }
                            )
                        }

                        // Экран с любимыми фильмами пользователя
                        composable ("favouritesScreen") {
                            FavouritesScreen(navController)
                        }
                    }
                }
            }
        }
    }
}