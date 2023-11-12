@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.mobile_moviescatalog2023.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile_moviescatalog2023.ViewModel.FeedbackViewModel
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.ViewModel.MovieDescriptionViewModel
import com.example.mobile_moviescatalog2023.ui.theme.*

// Диалоговое окно добавления/редактирования отзыва
@Composable
fun FeedbackDialog(descriptionVm: MovieDescriptionViewModel) {
    val vm: FeedbackViewModel = viewModel()
    vm.getValues(descriptionVm.myReview, descriptionVm.movieId.value)

    Dialog(
        onDismissRequest = { /*TODO*/ }
    ) {
        Column(
          modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(6.dp))
              .background(DarkGray700)
        ) {
            FeedbackLabel()
            RatingStars(vm)
            FeedbackText(vm)
            AnonymousFeedback(vm)
            FeedbackSaveButton(vm, descriptionVm)
            FeedbackCancelButton(vm, descriptionVm)
        }
    }
}

@Composable
fun FeedbackLabel() {
    Text(
        text = "Оставить отзыв",
        style = TextStyle(
            fontSize = 22.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        ),
        modifier = Modifier
            .padding(10.dp)
    )
}

// Оценка отзыва
@Composable
fun RatingStars(vm: FeedbackViewModel) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(16.dp, 16.dp, 16.dp, 0.dp)
    ) {
        for (i in 0..9) {
            Image(
                painter = if (i < vm.rating.value)
                    painterResource(id = R.drawable.filled_star)
                else
                    painterResource(id = R.drawable.empty_star),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        enabled = true,
                        onClick = {
                            vm.rating.value = i + 1
                            vm.checkFilling()
                        }
                    )
            )
        }
    }
}

// Ввод текста отзыва
@Composable
fun FeedbackText(vm: FeedbackViewModel) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = vm.message.value,
        onValueChange = {
            vm.message.value = it
            vm.checkFilling()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(16.dp, 8.dp, 16.dp, 0.dp),
        textStyle = TextStyle(
            color = Color.White,
            fontSize = 14.sp
        ),
        placeholder = {
            if (vm.message.value.isEmpty()) {
                Text(
                    text = "Напишите отзыв",
                    style = TextStyle(
                        color = Gray90,
                        fontSize = 14.sp
                    )
                )
            }
        },
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        shape = RoundedCornerShape(4.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Gray5E,
            unfocusedBorderColor = Gray5E,
            cursorColor = Color.White,
            textColor = Color.White,
            leadingIconColor = Color.White,
            trailingIconColor = Color.White
        )
    )
}

// Кнопка Анонимный отзыв
@Composable
fun AnonymousFeedback(vm: FeedbackViewModel) {
    Row(
        modifier = Modifier
            .padding(16.dp, 16.dp, 0.dp, 0.dp)
    ){
        Image(
            painter = if (!vm.isAnonymous.value)
                painterResource(id = R.drawable.empty_checkbox)
            else
                painterResource(id = R.drawable.filled_checkbox),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .clickable(
                    enabled = true,
                    onClick = {
                        vm.changeAnonymous()
                    }
                )
        )

        Text(
            text = "Анонимный отзыв",
            style = TextStyle(
                color = Color.White,
                fontSize = 14.sp
            ),
            modifier = Modifier
                .padding(8.dp, 0.dp)
        )
    }
}

// Кнопка Сохранить
@Composable
fun FeedbackSaveButton(vm: FeedbackViewModel, descriptionVm: MovieDescriptionViewModel) {
    vm.checkFilling()
    val isEnabled by remember { vm.isFilled }

    Button(
        enabled = isEnabled,
        onClick = {
            vm.saveFeedback()
            descriptionVm.updateFeedback()
        },
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(16.dp, 25.dp, 16.dp, 0.dp)
            .height(40.dp),
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
            text = stringResource(id = R.string.save),
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
fun FeedbackCancelButton(vm: FeedbackViewModel, descriptionVm: MovieDescriptionViewModel) {
    Button(
        onClick = {
            descriptionVm.closeFeedbackDialog()
            vm.cancel()
        },
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(16.dp, 8.dp, 16.dp, 10.dp)
            .height(40.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = DarkGray750),
        shape = RoundedCornerShape(10.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        )
    ) {
        Text (
            text = stringResource(id = R.string.cancel),
            style = TextStyle(
                color = AccentColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp
            )
        )
    }
}