@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.mobile_moviescatalog2023.View

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.widget.DatePicker
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
import androidx.compose.ui.platform.LocalContext
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
import com.example.mobile_moviescatalog2023.ViewModel.RegistrationData
import com.example.mobile_moviescatalog2023.ui.theme.*
import java.util.*

// Экран регистрации
@Composable
fun RegistrationScreen(
    onBackButtonClick: ()->Unit,
    onLoginClick: () -> Unit,
    onContinueButtonClick: ()->Unit
) {
    val isFilledLogin = remember{ mutableStateOf(false) }

    Column {
        FilmusHeaderWithBackButton(onBackButtonClick)
        Header()
        Name()
        Gender()
        Login(isFilledLogin, mutableStateOf(""))
        Email()
        DateOfBirth()
        ContinueButton(onContinueButtonClick)
        Spacer(modifier = Modifier.weight(1f))
        FooterRegistrationText (onLoginClick)
    }
}

// Календарь
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Calendar(dateOfBirth: MutableState<String>, isOpened: Boolean) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    calendar.time = Date()
    val date = remember { mutableStateOf("") }

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            date.value = "$mDayOfMonth/${mMonth+1}/$mYear"
        },
        year,
        month,
        day
    )

    if (isOpened)
        datePickerDialog.show()

    dateOfBirth.value = date.value.replace('/', '.')

    if (date.value != "") {
        val splitDate = date.value.split('/')
        RegistrationData.birthDate = "${splitDate[2]}-${splitDate[1]}-${splitDate[0]}T13:14:47.274Z"
    }
}

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
        onValueChange = {
            message = it
            RegistrationData.name = it.text
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
                RegistrationData.gender = if (maleSelected.value) 0 else 1
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
                RegistrationData.gender = if (maleSelected.value) 0 else 1
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
fun Login(isFilled: MutableState<Boolean>, username: MutableState<String>) {
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
            RegistrationData.userName = it.text
            username.value = it.text
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
        onValueChange = {
            email = it
            RegistrationData.email = it.text
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

// Ввод даты рождения пользователя
@Composable
fun DateOfBirth() {
    val isOpenedCalendar = remember{mutableStateOf(false)}
    val isClicked = remember{mutableStateOf(false)}
    val dateOfBirth = remember { mutableStateOf("") }

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
    ){
        Text(
            text = dateOfBirth.value,
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
        Image (
            imageVector = ImageVector.vectorResource(id = R.drawable.date_icon),
            contentDescription = null,
            modifier = Modifier
                .clickable (
                    enabled = true,
                    onClick = {
                        isOpenedCalendar.value = true
                        isClicked.value = true
                    }
                )
        )
    }

    if (isOpenedCalendar.value) {
        Calendar(dateOfBirth, isClicked.value)
        isClicked.value = false
    }
}

// Кнопка Продолжить
@Composable
fun ContinueButton(onContinueButtonClick: () -> Unit) {
    Button(
        onClick = { onContinueButtonClick() },
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
                text = stringResource(id = R.string.already_have_account) + ' ',
                fontSize = 17.sp,
                color = GrayC4
            )
            Text(
                text = stringResource(id = R.string.do_sign_in),
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