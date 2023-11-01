@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)

package com.example.mobile_moviescatalog2023.ui.theme

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.BottomNavBar
import java.util.*

// Профиль пользователя
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen(navController: NavController) {
    Column {
        Scaffold(
            bottomBar = {
                Divider(Modifier.width(1.dp), Gray40)
                BottomNavBar(navController)
            }
        ) {
            Column {
                ProfilePicture()
                ProfileName()

                Column(Modifier.verticalScroll(rememberScrollState())) {
                    ProfileEmailField()
                    ProfileAvatarSourceField()
                    ProfileNameField()
                    ProfileGender()
                    ProfileDateOfBirth()

                    ProfileSaveButton()
                    ProfileCancelButton()
                    Box(modifier = Modifier.height(64.dp))
                }
            }

        }
    }
}

// Аватарка пользователя
@Composable
fun ProfilePicture() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp, 16.dp, 4.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.media3),
            contentDescription = "null",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
                .align(Alignment.Center)
        )
    }
}

// Имя пользователя
@Composable
fun ProfileName() {
    Text(
        text = "Ivan ivan ivan",
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 26.sp
        ),
        modifier = Modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

// Поле с email пользователя
@Composable
fun ProfileEmailField() {
    ProfileTextField(
        label = stringResource(id = R.string.email),
        borderColor = Gray5E,
        bgColor = DarkGray700
    )
}

// Поле со ссылкой на аватар пользователя
@Composable
fun ProfileAvatarSourceField() {
    ProfileTextField(
        label = "Ссылка на аватар",
        borderColor = Gray5E,
        bgColor = DarkGray700
    )
}

// Поле с именем пользователя
@Composable
fun ProfileNameField() {
    ProfileTextField(
        label = stringResource(id = R.string.name),
        borderColor = Gray5E,
        bgColor = DarkGray700
    )
}

// Поле с данными профиля пользователя
@Composable
fun ProfileTextField(
    label: String,
    borderColor: Color,
    bgColor: Color
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
    var textFieldValue by remember{ mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
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
                        color = borderColor,
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

// Пол пользователя
@Composable
fun ProfileGender() {
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
        val maleSelected = remember {
            mutableStateOf(true)
        }

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

// Календарь
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileCalendar(isClicked: MutableState<Boolean>, dateOfBIrthDisplay: MutableState<String>) {
    val calendar = Calendar.getInstance()
    calendar.time = Date()

    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = calendar.timeInMillis)
    var selectedDate by remember { mutableStateOf(calendar.timeInMillis) }

    val displayFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.ROOT)
    //val birthDateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)

    if (isClicked.value){
        DatePickerDialog(
            onDismissRequest = {
                isClicked.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                    isClicked.value = false
                    selectedDate = datePickerState.selectedDateMillis!!
                    dateOfBIrthDisplay.value = displayFormatter.format(Date(selectedDate)).replace('-', '.')
                 //   viewModel.birthDate.value = birthDateFormatter.format(Date(selectedDate)).replace('-', '.') + "T13:14:47.274Z"
                }) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                    isClicked.value = false
                }) {
                    Text(text = "Cancel")
                }
            }
        ){
            DatePicker(
                state = datePickerState,
               // Modifier.background(Color(0xFF303030)),

               /* colors = DatePickerDefaults.colors(
                    containerColor = Color(0xFF303030),
                    titleContentColor = Color.White,
                    headlineContentColor = AccentColor,
                    weekdayContentColor = Color.White,
                    subheadContentColor = Color.White,
                    yearContentColor = Color.White,
                    //disabledYearContentColor = Color.White,
                    currentYearContentColor = Color.White,
                    selectedYearContentColor = AccentColor,
                   // disabledSelectedYearContentColor = Color.Green,
                    selectedYearContainerColor = Color.White,
                  //  disabledSelectedYearContainerColor = Color.Green,
                    dayContentColor = Color.White,
                    disabledDayContentColor = GrayC4,
                    selectedDayContentColor = Color.White,
                    disabledSelectedDayContentColor = Color.White,
                    selectedDayContainerColor = AccentColor,
                   // disabledSelectedDayContainerColor: Color,
                    todayContentColor = Color.White,
                    todayDateBorderColor = AccentColor,
                    dayInSelectionRangeContentColor = Color.White,
                   // dayInSelectionRangeContainerColor = Color,
                  //  dividerColor = Color.White,
                   // dateTextFieldColors: TextFieldColors
                )*/
            )
        }
    }
}

// Поле с датой рождения пользователя
@Composable
fun ProfileDateOfBirth() {
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

    val dateOfBirthDisplay = remember {mutableStateOf("")}
    val isOpenedCalendar = remember { mutableStateOf(false) }
    val isClicked = remember { mutableStateOf(false) }

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
            text = dateOfBirthDisplay.value,
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

    ProfileCalendar(isClicked, dateOfBirthDisplay)
    isClicked.value = false
}

// Кнопка Сохранить
@Composable
fun ProfileSaveButton() {
    val isEnabled = false

    Button(
        enabled = isEnabled,
        onClick = {},
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(16.dp, 20.dp, 16.dp, 0.dp)
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
            text = "Сохранить",
            style = TextStyle(
                color = if (isEnabled) Color.White else WhiteTransparent,
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp
            )
        )
    }
}

// Кнопка Отмена
@Composable
fun ProfileCancelButton() {
    // Кнопка Войти
    Button(
        onClick = { },
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
            text = "Отмена",
            style = TextStyle(
                color = AccentColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp
            )
        )
    }
}