@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.mobile_moviescatalog2023.ui.theme

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.ViewModel.LoginViewModel
import com.example.mobile_moviescatalog2023.ViewModel.RegistrationViewModel
import java.util.*

// Профиль пользователя
@Composable
fun Profile() {
    Column {
        ProfilePicture()
        ProfileName()

        ProfileEmailField()
        ProfileAvatarSourceField()
        ProfileNameField()
        ProfileGender()
        ProfileDateOfBirth()

        ProfileSaveButton()
        ProfileCancelButton()
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

    if (isClicked.value){
        datePickerDialog.show()
        isClicked.value = false
    }

    val birthDate = date.value.replace('/', '.')

    if (date.value != "") {
        val splitDate = date.value.split('/')

        val year = splitDate[2]
        val month = if (splitDate[1].length == 1) '0' + splitDate[1] else splitDate[1]
        val day = if (splitDate[0].length == 1) '0' + splitDate[0] else splitDate[0]

       // viewModel.birthDate.value = "${year}-${month}-${day}T13:14:47.274Z"
        dateOfBIrthDisplay.value = birthDate
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