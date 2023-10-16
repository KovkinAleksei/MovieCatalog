package com.example.mobile_moviescatalog2023

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.ui.theme.Accent
import com.example.mobile_moviescatalog2023.ui.theme.MobileMoviesCatalog2023Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileMoviesCatalog2023Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RegistrationOrLoginScreen("Android")
                }
            }
        }
    }
}

// Кнопка возврата
@Composable
fun BackButton() {
    Box(
        modifier = Modifier
            .height(40.dp)
            .width(40.dp),
        contentAlignment = Alignment.Center
    ) {
        Image (
            painter = painterResource(id = R.drawable.back_button),
            contentDescription = null,
            modifier = Modifier
                .size(12.dp)
        )
    }
}

// Заголовок Fильмус
@Composable
fun FilmusHeader() {
    Text(
        modifier = Modifier
            .fillMaxWidth(1f),
        text = stringResource(id = R.string.cinema_name),
        textAlign = TextAlign.Center,
        color = Accent,
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
    )
}

// Картинка на экране выбора регистрации/входа
@Composable
fun MovieIcon() {
    Image (
        painter = painterResource(id = R.drawable.movie_icon),
        contentDescription = null,
        modifier = Modifier
            .padding(16.dp, 35.dp, 16.dp, 0.dp)
            .fillMaxWidth(1f),
        alignment = Alignment.Center,
        contentScale = ContentScale.FillWidth
    )
}

// Экран выбора регистрации или авторизации пользователя
@Composable
fun RegistrationOrLoginScreen(name: String) {
    Column {
        Box(
            modifier = Modifier
                .height(60.dp)
                .padding(16.dp)
        ) {
            FilmusHeader()
            BackButton()
        }

        MovieIcon()

        // Надпись "Погрузись в мир кино"
        Text (
            modifier = Modifier
                .padding(0.dp, 35.dp, 0.dp, 0.dp)
                .fillMaxWidth(1f),
            text = stringResource(id = R.string.registration_slogan),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
            textAlign = TextAlign.Center
        )

        // Описание онлайн кинотеатра
        Text (
            modifier = Modifier
                .padding(16.dp, 8.dp, 16.dp, 0.dp)
                .fillMaxWidth(1f),
            text = stringResource(id = R.string.cinema_description),
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.White
            ),
            textAlign = TextAlign.Center
        )

        /*Button(
            onClick = { *//*TODO*//* },
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(16.dp, 35.dp, 16.dp, 0.dp)
        ) {
            Text (
                text = "Click"
            )
        }

        Button(
            onClick = { *//*TODO*//* },
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(16.dp, 16.dp, 16.dp, 0.dp),

            ) {
            Text (
                text = "Click"
            )
        }*/
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MobileMoviesCatalog2023Theme {
        RegistrationOrLoginScreen("Android")
    }
}