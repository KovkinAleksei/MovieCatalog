@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)

package com.example.mobile_moviescatalog2023.View

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.ViewModel.RegistrationViewModel
import com.example.mobile_moviescatalog2023.ui.theme.*
import java.util.*

// Экран регистрации
@Composable
fun RegistrationScreen(
    onBackButtonClick: () -> Unit,
    onLoginClick: () -> Unit,
    onContinueButtonClick: () -> Unit
) {
    val viewModel: RegistrationViewModel = viewModel()

    Column {
        FilmusHeaderWithBackButton(onBackButtonClick)
        Header()
        Name(viewModel)
        Gender(viewModel)
        Login(viewModel)
        Email(viewModel)
        DateOfBirth(viewModel)
        if (viewModel.errorMessage.value != "")
            EmailErrorMessage(viewModel)
        ContinueButton(viewModel, onContinueButtonClick)
        Spacer(modifier = Modifier.weight(1f))
        FooterRegistrationText(onLoginClick)
    }
}

// Календарь
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Calendar(viewModel: RegistrationViewModel) {
    val calendar = Calendar.getInstance()
    calendar.time = Date()

    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = calendar.timeInMillis)
    var selectedDate by remember { mutableStateOf(calendar.timeInMillis) }

    val displayFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.ROOT)
    val birthDateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)

    if (viewModel.isClicked.value) {
        DatePickerDialog(
            colors = DatePickerDefaults.colors(
                containerColor = Color(0xFF303030)
            ),
            onDismissRequest = {
                viewModel.isClicked.value = false
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.isClicked.value = false
                    selectedDate = datePickerState.selectedDateMillis!!
                    viewModel.dateOfBIrthDisplay.value =
                        displayFormatter.format(Date(selectedDate)).replace('-', '.')
                    viewModel.birthDate.value =
                        birthDateFormatter.format(Date(selectedDate)) + "T13:14:47.274Z"
                }) {
                    Text(text = stringResource(id = R.string.confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    viewModel.isClicked.value = false
                }) {
                    Text(text = stringResource(id = R.string.cancel_calendar))
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                Modifier.background(Color(0xFF303030)),

                colors = DatePickerDefaults.colors(
                    containerColor = Color(0xFF303030),
                    titleContentColor = Color.White,
                    headlineContentColor = AccentColor,
                    weekdayContentColor = Color.White,
                    subheadContentColor = Color.White,
                    yearContentColor = Color.White,
                    currentYearContentColor = Color.White,
                    selectedYearContentColor = AccentColor,
                    selectedYearContainerColor = Color.White,
                    dayContentColor = Color.White,
                    disabledDayContentColor = GrayC4,
                    selectedDayContentColor = Color.White,
                    disabledSelectedDayContentColor = Color.White,
                    selectedDayContainerColor = AccentColor,
                    todayContentColor = Color.White,
                    todayDateBorderColor = AccentColor,
                    dayInSelectionRangeContentColor = Color.White
                )
            )
        }
    }
}

// Кнопка возврата
@Composable
fun BackButton(onBackButtonClick: () -> Unit) {
    Image(
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
    ) {
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

// Выбор пола пользователя
@Composable
fun Gender(viewModel: RegistrationViewModel) {
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
                if (!viewModel.maleSelected.value)
                    viewModel.maleSelected.value = !viewModel.maleSelected.value
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (viewModel.maleSelected.value) Color.White else Gray40
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
                    color = if (viewModel.maleSelected.value) Gray40 else Gray90,
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
                if (viewModel.maleSelected.value)
                    viewModel.maleSelected.value = !viewModel.maleSelected.value
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (viewModel.maleSelected.value) Gray40 else Color.White
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
                    color = if (viewModel.maleSelected.value) Gray90 else Gray40,
                    fontSize = 17.sp
                )
            )
        }
    }
}

// Поле с именем пользователя
@Composable
fun Name(vm: RegistrationViewModel) {
    RegistrationTextField(
        label = stringResource(id = R.string.name),
        bgColor = DarkGray700,
        vm.name
    )
}

// Поле с логином пользователя
@Composable
fun Login(vm: RegistrationViewModel) {
    RegistrationTextField(
        label = stringResource(id = R.string.login),
        bgColor = DarkGray700,
        vm.userName
    )
}

// Поле с электронной почтой
@Composable
fun Email(vm: RegistrationViewModel) {
    RegistrationTextField(
        label = stringResource(id = R.string.email),
        bgColor = DarkGray700,
        vm.email
    )
}

// Поле с данными профиля пользователя
@Composable
fun RegistrationTextField(
    label: String,
    bgColor: Color,
    fieldValue: MutableState<String>
) {
    // Название поля
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

    // Поле с данными
    var textFieldValue by remember { fieldValue }
    val keyboardController = LocalSoftwareKeyboardController.current
    val isFocused = remember { mutableStateOf(true) }

    BasicTextField(
        modifier = Modifier
            .onFocusChanged {
                isFocused.value = !isFocused.value
            },
        value = textFieldValue,
        singleLine = true,
        cursorBrush = SolidColor(Color.White),
        onValueChange = {
            textFieldValue = it
        },
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(55.dp)
                    .padding(16.dp, 8.dp, 16.dp, 0.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(bgColor)
                    .border(
                        width = 1.dp,
                        color = if (isFocused.value)
                            AccentColor
                        else
                            Gray5E,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(12.dp)
            ) {
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
fun DateOfBirth(viewModel: RegistrationViewModel) {
    // Надпись Дата рождения
    Text(
        modifier = Modifier
            .padding(16.dp, 15.dp, 0.dp, 0.dp),
        text = stringResource(id = R.string.date_of_birth),
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
    ) {
        Text(
            text = viewModel.dateOfBIrthDisplay.value,
            style = TextStyle(
                color = Color.White,
                fontSize = 17.sp
            )
        )
        Spacer(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        )
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.date_icon),
            contentDescription = null,
            modifier = Modifier
                .clickable(
                    enabled = true,
                    onClick = {
                        viewModel.isOpenedCalendar.value = true
                        viewModel.isClicked.value = true
                    }
                )
        )
    }

    Calendar(viewModel)
    viewModel.isClicked.value = false
}

// Вывод сообщения об ошибке
@Composable
fun EmailErrorMessage(viewModel: RegistrationViewModel) {
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

// Кнопка Продолжить
@Composable
fun ContinueButton(viewModel: RegistrationViewModel, onContinueButtonClick: () -> Unit) {
    val isEnabled =
        !viewModel.userName.value.isEmpty() && !viewModel.name.value.isEmpty() && !viewModel.email.value.isEmpty() &&
                (viewModel.birthDate.value.length > 0)
    Button(
        enabled = isEnabled,
        onClick = {
            viewModel.continueButtonClick()

            if (viewModel.errorMessage.value == "")
                onContinueButtonClick()
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
            text = stringResource(id = R.string.continueButton),
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
fun FooterRegistrationText(onLoginClick: () -> Unit) {
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
                color = GrayC4
            )
            Text(
                text = stringResource(id = R.string.do_sign_in),
                fontSize = 17.sp,
                color = AccentColor,
                modifier = Modifier
                    .clickable(
                        enabled = true,
                        onClick = { onLoginClick() }
                    )
            )
        }
    }
}