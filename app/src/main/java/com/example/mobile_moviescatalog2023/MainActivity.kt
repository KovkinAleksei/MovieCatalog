package com.example.mobile_moviescatalog2023

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
                        startDestination = splashScr
                    ) {
                        // Загрузочный экран
                        composable(splashScr) {
                            SplashScreen(navController = navController)
                        }

                        // Экран выбора регистрации или авторизации
                        composable(regOrLogScreen) {
                            RegistrationOrLoginScreen(
                                onRegButtonClick = {
                                    navController.navigate(registrationScreen)
                                },

                                onLoginButtonClick = {
                                    navController.navigate(loginScreen)
                                }
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
                        composable(loginScreen) {
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
                        composable(mainScreen) { navBackResult ->
                            navBackResult.savedStateHandle.get<Boolean>(isBack)?.let {
                                MainScreen(navController)
                            }

                            MainScreen(navController)
                        }

                        // Экран с профилем пользователя
                        composable(profileScreen) {
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
                        composable(favouritesScreen) { navBackResult ->
                            navBackResult.savedStateHandle.get<Boolean>(isBack)?.let {
                                MainScreen(navController)
                            }

                            FavouritesScreen(navController)
                        }

                        // Экран с описанием фильма
                        composable(
                            "${descriptionScreen}/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.StringType })
                        ) { navBackStackEntry ->
                            val id = navBackStackEntry.arguments?.getString("id")
                            MovieDescriptonScreen(id, navController) {
                                navController.navigate(mainScreen) {
                                    popUpTo(mainScreen) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}