@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.mobile_moviescatalog2023.View

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.ui.theme.*

// Кнопка возврата
@Composable
fun BackButton(onBackButtonClick: () -> Unit) {
    Image (
        modifier = Modifier
            .height(30.dp)
            .size(7.dp)
            .width(40.dp)
            .clickable(
                enabled = true,
                onClick = { onBackButtonClick() }
            ),
        imageVector = ImageVector.vectorResource(id = R.drawable.back_arrow),
        contentDescription = null
    )
}

// Заголовок Fильмус с кнопкой возврата
@Composable
fun FilmusHeaderWithBackButton(onBackButtonClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(16.dp)
    ){
        BackButton(onBackButtonClick)

        Text(
            modifier = Modifier
                .fillMaxWidth(1f),
            text = stringResource(id = R.string.cinema_name),
            textAlign = TextAlign.Center,
            color = AccentColor,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

// Заголовок экрана
@Composable
fun Header() {
    Text(
        modifier = Modifier
            .fillMaxWidth(1f),
        text = stringResource(id = R.string.registration),
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.White
        ),
        textAlign = TextAlign.Center
    )
}

// Ввод имени пользователя
@Composable
fun Name() {
    // Надпись Имя
    Text(
        modifier = Modifier
            .padding(16.dp, 15.dp, 0.dp, 0.dp),
        text = stringResource(id = R.string.name),
        style = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 17.sp,
            color = Color.White
        )
    )

    // Поле ввода имени
    var message by remember{ mutableStateOf(TextFieldValue(""))}
    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        value = message,
        singleLine = true,
        cursorBrush = SolidColor(Color.White),
        onValueChange = {message = it},
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(55.dp)
                    .padding(16.dp, 8.dp, 16.dp, 0.dp)
                    .border(
                        width = 1.dp,
                        color = Gray5E,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(12.dp)
            ){
                innerTextField()
            }
        },
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        textStyle = TextStyle(
            color = Color.White,
            fontSize = 17.sp
        )
    )
}

// Выбор пола пользователя
@Composable
fun Gender() {
    // Надпись пол
    Text(
        modifier = Modifier
            .padding(16.dp, 16.dp, 0.dp, 0.dp),
        text = stringResource(id = R.string.gender),
        style = TextStyle(
            color = Color.White,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )
    )

    // Кнопки
    val maleSelected = remember{ mutableStateOf(true) }

    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(16.dp, 8.dp, 16.dp, 0.dp)
            .background(
                color = Gray40,
                shape = RoundedCornerShape(10.dp)
            )
            .height(55.dp)
            .padding(2.dp)
    ) {
        // Кнопка Мужчина
        Button(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(1f)
                .padding(1.dp, 0.dp, 0.dp, 0.dp),
            onClick = {
                if (!maleSelected.value)
                    maleSelected.value = !maleSelected.value
                      },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (maleSelected.value) Color.White else Gray40
            ),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp
            )
        ) {
            Text(
                text = stringResource(id = R.string.male),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    color = if (maleSelected.value) Gray40 else Gray90,
                    fontSize = 17.sp
                )
            )
        }

        // Кнопка Женщина
        Button(
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(1f)
                .padding(0.dp, 0.dp, 1.dp, 0.dp),
            onClick = {
                if (maleSelected.value)
                    maleSelected.value = !maleSelected.value
                      },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (maleSelected.value) Gray40 else Color.White
            ),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp
            )
        ) {
            Text(
                text = stringResource(id = R.string.female),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    color = if (maleSelected.value) Gray90 else Gray40,
                    fontSize = 17.sp
                )
            )
        }
    }

}

// Ввод логина пользователя
@Composable
fun Login(isFilled: MutableState<Boolean>) {
    // Надпись Логин
    Text(
        modifier = Modifier
            .padding(16.dp, 15.dp, 0.dp, 0.dp),
        text = stringResource(id = R.string.login),
        style = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 17.sp,
            color = Color.White
        )
    )

    // Поле ввода логина
    var login by remember{ mutableStateOf(TextFieldValue(""))}
    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        value = login,
        singleLine = true,
        cursorBrush = SolidColor(Color.White),
        onValueChange = {
            login = it
            isFilled.value = login.text.length > 0
                        },
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(55.dp)
                    .padding(16.dp, 8.dp, 16.dp, 0.dp)
                    .border(
                        width = 1.dp,
                        color = Gray5E,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(12.dp)
            ){
                innerTextField()
            }
        },
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        textStyle = TextStyle(
            color = Color.White,
            fontSize = 17.sp
        )
    )
}

// Ввод электронной почты пользователя
@Composable
fun Email() {
    // Надпись Электронная почта
    Text(
        modifier = Modifier
            .padding(16.dp, 15.dp, 0.dp, 0.dp),
        text = stringResource(id = R.string.email),
        style = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 17.sp,
            color = Color.White
        )
    )

    // Поле ввода электронной почты
    var email by remember{ mutableStateOf(TextFieldValue(""))}
    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        value = email,
        singleLine = true,
        cursorBrush = SolidColor(Color.White),
        onValueChange = {email = it},
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(55.dp)
                    .padding(16.dp, 8.dp, 16.dp, 0.dp)
                    .border(
                        width = 1.dp,
                        color = Gray5E,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(12.dp)
            ){
                innerTextField()
            }
        },
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        textStyle = TextStyle(
            color = Color.White,
            fontSize = 17.sp
        )
    )
}

// Ввод даты рождения пользователя
@Composable
fun DateOfBirth() {
    // Надпись Дата рождения
    Text(
        modifier = Modifier
            .padding(16.dp, 15.dp, 0.dp, 0.dp),
        text = stringResource(id = R.string.dateOfBirth),
        style = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 17.sp,
            color = Color.White
        )
    )

    // Поле выбора даты рождения
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(55.dp)
            .padding(16.dp, 8.dp, 16.dp, 0.dp)
            .border(
                width = 1.dp,
                color = Gray5E,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(12.dp)
    ){
        Spacer(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        )

        Image (
            imageVector = ImageVector.vectorResource(id = R.drawable.date_icon),
            contentDescription = null,
            modifier = Modifier
                .clickable (
                    enabled = true,
                    onClick = {}
                )
        )
    }
}

// Кнопка Продолжить
@Composable
fun ContinueButton() {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(16.dp, 24.dp, 16.dp, 0.dp)
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = AccentColor),
        shape = RoundedCornerShape(10.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        )
    ) {
        Text (
            text = stringResource(id = R.string.continueButton),
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp
            )
        )
    }
}

// Текст внизу экрана
@Composable
fun FooterRegistrationText(onLoginClick: ()->Unit) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(1f),
        contentAlignment = Alignment.Center
    ) {
        Row {
            Text(
                text = "Уже есть аккаунт? ",
                fontSize = 17.sp,
                color = GrayC4
            )
            Text(
                text = "Войдите",
                fontSize = 17.sp,
                color = AccentColor,
                modifier = Modifier
                    .clickable (
                        enabled = true,
                        onClick = {onLoginClick()}
                    )
            )
        }
    }
}

// Экран регистрации
@Composable
fun RegistrationScreen(onBackButtonClick: ()->Unit, onLoginClick: () -> Unit) {
    val isFilledLogin = remember{ mutableStateOf(false) }

    Column {
        FilmusHeaderWithBackButton(onBackButtonClick)
        Header()
        Name()
        Gender()
        Login(isFilledLogin)
        Email()
        DateOfBirth()
        ContinueButton()
        Spacer(modifier = Modifier.weight(1f))
        FooterRegistrationText (onLoginClick)
    }
}