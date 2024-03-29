@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.mobile_moviescatalog2023.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.ViewModel.RegistrationPasswordViewModel
import com.example.mobile_moviescatalog2023.ui.theme.*

// Экран выбора и подтверждения пароля
@Composable
fun RegistrationPasswordScreen(
    onBackButtonClick: () -> Unit,
    onSignInClick: () -> Unit,
    onRegistrationButtonClick: () -> Unit
) {
    val viewModel: RegistrationPasswordViewModel = viewModel()

    Column {
        FilmusHeaderWithBackButton(onBackButtonClick)
        Header()
        ChoosingPassword(viewModel)

        if (viewModel.errorMessage.value != "")
            PasswordErrorMessage(viewModel)

        RegistrationButton(viewModel, onRegistrationButtonClick)
        Spacer(modifier = Modifier.weight(1f))
        FooterPasswordRegistrationText(onSignInClick)
    }
}

// Вывод ошибки
@Composable
fun PasswordErrorMessage(viewModel: RegistrationPasswordViewModel) {
    Text(
        text = viewModel.errorMessage.value,
        style = TextStyle(
            fontSize = 16.sp,
            color = errorColor
        ),
        modifier = Modifier
            .padding(16.dp, 4.dp, 0.dp, 0.dp)
    )
}

// Поле ввода пароля
@Composable
fun FillingPassword(
    viewModel: RegistrationPasswordViewModel,
    password: MutableState<TextFieldValue>,
    isFilledPassword: MutableState<Boolean>,
    label: String
) {
    // Надпись Пароль
    Text(
        modifier = Modifier
            .padding(16.dp, 15.dp, 0.dp, 0.dp),
        text = label,
        style = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 17.sp,
            color = Color.White
        )
    )

    // Поле ввода пароля
    val keyboardController = LocalSoftwareKeyboardController.current
    val showPassword = remember { mutableStateOf(false) }
    val isFocused = remember { mutableStateOf(true) }

    BasicTextField(
        modifier = Modifier
            .onFocusChanged {
                isFocused.value = !isFocused.value
            },
        value = password.value,
        singleLine = true,
        cursorBrush = SolidColor(Color.White),
        onValueChange = {
            password.value = it
            isFilledPassword.value = password.value.text.length > 0
            viewModel.errorMessage.value = ""
        },
        visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(55.dp)
                    .padding(16.dp, 8.dp, 16.dp, 0.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(if (viewModel.errorMessage.value == "") DarkGray700 else errorTransparent)
                    .border(
                        width = 1.dp,
                        color = if (viewModel.errorMessage.value.isEmpty() && !isFocused.value)
                            Gray5E
                        else if (viewModel.errorMessage.value.isEmpty() && isFocused.value)
                            AccentColor
                        else
                            errorColor,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(12.dp)
            ) {
                innerTextField()
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.opened_eye),
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

// Ввод и подтверждение пароля
@Composable
fun ChoosingPassword(viewModel: RegistrationPasswordViewModel) {
    val isFilledRegistratonPassword = remember { mutableStateOf(false) }

    val firstPassword = remember { mutableStateOf(TextFieldValue("")) }
    val secondPassword = remember { mutableStateOf(TextFieldValue("")) }

    FillingPassword(
        viewModel = viewModel,
        password = firstPassword,
        label = stringResource(id = R.string.password),
        isFilledPassword = isFilledRegistratonPassword
    )

    FillingPassword(
        viewModel = viewModel,
        password = secondPassword,
        label = stringResource(id = R.string.repeat_password),
        isFilledPassword = isFilledRegistratonPassword
    )

    viewModel.isEnabledRegButton.value =
        firstPassword.value.text == secondPassword.value.text && isFilledRegistratonPassword.value

    if (viewModel.isEnabledRegButton.value)
        viewModel.password.value = firstPassword.value.text
}

// Кнопка Зарегистрироваться
@Composable
fun RegistrationButton(
    viewModel: RegistrationPasswordViewModel,
    onRegistrationButtonClick: () -> Unit
) {
    Button(
        enabled = viewModel.isEnabledRegButton.value,
        onClick = {
            viewModel.onRegistrationButtonClick()
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
        Text(
            text = stringResource(id = R.string.registration_last_button),
            style = TextStyle(
                color = if (viewModel.isEnabledRegButton.value) Color.White else WhiteTransparent,
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp
            )
        )
    }

    if (viewModel.isRegistrationAvailable.value) {
        onRegistrationButtonClick()
        viewModel.isRegistrationAvailable.value = false
    }
}

// Текст внизу экрана
@Composable
fun FooterPasswordRegistrationText(onSignInClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(1f),
        contentAlignment = Alignment.Center
    ) {
        Row {
            Text(
                text = stringResource(id = R.string.already_have_account) + ' ',
                fontSize = 17.sp,
                color = Color.White
            )
            Text(
                text = stringResource(id = R.string.do_sign_in),
                fontSize = 17.sp,
                color = AccentColor,
                modifier = Modifier
                    .clickable(
                        enabled = true,
                        onClick = { onSignInClick() }
                    )
            )
        }
    }
}