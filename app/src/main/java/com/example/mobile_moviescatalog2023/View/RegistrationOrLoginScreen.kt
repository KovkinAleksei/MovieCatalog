package com.example.mobile_moviescatalog2023.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.ui.theme.AccentColor
import com.example.mobile_moviescatalog2023.ui.theme.DarkGray750
import com.example.mobile_moviescatalog2023.R

// Экран выбора регистрации или авторизации пользователя
@Composable
fun RegistrationOrLoginScreen(
    onRegButtonClick: () -> Unit,
    onLoginButtonClick: () -> Unit,
) {
    Column {
        MovieIcon()
        CinemaDescription()
        Buttons(onRegButtonClick, onLoginButtonClick)
    }
}

// Картинка на экране выбора регистрации/входа
@Composable
fun MovieIcon() {
    Image (
        imageVector = ImageVector.vectorResource(id = R.drawable.cinema_icon),
        contentDescription = null,
        modifier = Modifier
            .padding(16.dp, 80.dp, 16.dp, 0.dp)
            .fillMaxWidth(1f),
        contentScale = ContentScale.FillWidth
    )
}

// Описание кинотеатра
@Composable
fun CinemaDescription() {
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
}

// Кнопки входа и регистрации
@Composable
fun Buttons(onRegButtonClick: () -> Unit, onLoginButtonClick: () -> Unit) {
    // Кнопка регистрации
    Button(
        onClick = { onRegButtonClick() },
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(16.dp, 35.dp, 16.dp, 0.dp)
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = AccentColor),
        shape = RoundedCornerShape(10.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        )
    ) {
        Text (
            text = stringResource(id = R.string.registration_button),
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp
            )
        )
    }

    // Кнопка Войти
    Button(
        onClick = { onLoginButtonClick() },
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(16.dp, 16.dp, 16.dp, 0.dp)
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = DarkGray750),
        shape = RoundedCornerShape(10.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        )
    ) {
        Text (
            text = stringResource(id = R.string.login_button),
            style = TextStyle(
                color = AccentColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp
            )
        )
    }
}
