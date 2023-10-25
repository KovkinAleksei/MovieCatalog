@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.mobile_moviescatalog2023.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.ViewModel.LoginViewModel
import com.example.mobile_moviescatalog2023.ViewModel.RegistrationData
import com.example.mobile_moviescatalog2023.ui.theme.*

// Экран авторизации пользователя
@Composable
fun LoginScreen(onBackButtonClick: () -> Unit, onRegistrationClick: () -> Unit, onLoginButtonClick: () -> Unit) {
    val viewModel: LoginViewModel = viewModel()

    Column {
        FilmusHeaderWithBackButton {
            onBackButtonClick()
        }

        LoginHeader()
        LoginUser(viewModel)
        Password(viewModel)
        LoginButton(viewModel, onLoginButtonClick)
        Spacer(modifier = Modifier.weight(1f))
        FooterText(onRegistrationClick)
    }
}

// Заголовок экрана
@Composable
fun LoginHeader() {
    Text(
        modifier = Modifier
            .fillMaxWidth(1f),
        text = stringResource(id = R.string.signing_in),
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.White
        ),
        textAlign = TextAlign.Center
    )
}

// Ввод логина пользователя
@Composable
fun LoginUser(viewModel: LoginViewModel) {
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
            viewModel.isFilledLogin.value = login.text.length > 0
            RegistrationData.userName = it.text
            viewModel.username.value = it.text
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

// Ввод пароля
@Composable
fun Password(viewModel: LoginViewModel) {
    // Надпись Пароль
    Text(
        modifier = Modifier
            .padding(16.dp, 15.dp, 0.dp, 0.dp),
        text = stringResource(id = R.string.password),
        style = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 17.sp,
            color = Color.White
        )
    )

    // Поле ввода пароля
    var password by remember{ mutableStateOf(TextFieldValue(""))}
    val keyboardController = LocalSoftwareKeyboardController.current
    val showPassword = remember{mutableStateOf(false)}

    BasicTextField(
        value = password,
        singleLine = true,
        cursorBrush = SolidColor(Color.White),
        onValueChange = {
            password = it
            viewModel.isFilledPassword.value = password.text.length > 0
            viewModel.password.value = it.text
                        },
        visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
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
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
                Image (
                    imageVector = ImageVector.vectorResource(id = R.drawable.open_eye),
                    contentDescription = null,
                    modifier = Modifier
                        .size(22.dp)
                        .clickable(
                            enabled = true,
                            onClick = { showPassword.value = !showPassword.value }
                        )
                )
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

// Кнопка Войти
@Composable
fun LoginButton(viewModel: LoginViewModel, onLoginButtonClick: () -> Unit) {
    val isEnabled = viewModel.isFilledLogin.value && viewModel.isFilledPassword.value

    Button(
        enabled = isEnabled,
        onClick = {
            viewModel.login()
            onLoginButtonClick()
        },
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(16.dp, 24.dp, 16.dp, 0.dp)
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = AccentColor,
            disabledBackgroundColor = AccentColorTransparent
        ),
        shape = RoundedCornerShape(10.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        )
    ) {
        Text (
            text = stringResource(id = R.string.sign_in),
            style = TextStyle(
                color = if (isEnabled) Color.White else WhiteTransparent,
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp
            )
        )
    }
}

// Текст внизу экрана
@Composable
fun FooterText(onRegistrationClick: ()->Unit) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(1f),
        contentAlignment = Alignment.Center
    ) {
        Row {
            Text(
                text = stringResource(id = R.string.still_no_account) + ' ',
                fontSize = 17.sp,
                color = GrayC4
            )
            Text(
                text = stringResource(id = R.string.registrate),
                fontSize = 17.sp,
                color = AccentColor,
                modifier = Modifier
                    .clickable (
                        enabled = true,
                        onClick = {onRegistrationClick()}
                    )
            )
        }
    }
}
