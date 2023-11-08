@file:OptIn(ExperimentalLayoutApi::class)

package com.example.mobile_moviescatalog2023.View

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.ViewModel.MovieDescriptionViewModel
import com.example.mobile_moviescatalog2023.ui.theme.*

// Экран с описанием фильма
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieDescriptonScreen() {
    val vm: MovieDescriptionViewModel = viewModel()

    Scaffold(
        topBar = {
            TopBar()
        }
    ) {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            MoviePoster(vm)
            MovieHeader(vm)
            Description(vm)
            MovieGenres()
            MovieGenresList(vm)
            AboutMovie()
            MovieInfo(vm)
            FeedbackLable()
            FeedbackList(vm)
        }
    }
}

// Верхняя часть экрана
@Composable
fun TopBar() {
    Box(
        modifier = Modifier
            .padding(16.dp, 10.dp, 0.dp, 10.dp)
            .fillMaxWidth()
    ) {
        BackButton {
        }
    }
}

// Постер с фильмом
@Composable
fun MoviePoster(vm: MovieDescriptionViewModel) {
    Box (){
        val density = LocalDensity.current.density

        Image(
            painter = painterResource(id = vm.moviePoster),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.72f),
        )
        Box(
            modifier = Modifier
                .height(545.dp)
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, DarkGray700),
                        startY = 300f * density,
                        endY = 500 * density
                    )
                )
        )
    }
}

// Строка с оценкой, названием и добавлением в любимые фильмы
@Composable
fun MovieHeader(vm: MovieDescriptionViewModel) {
    // Оценка фильма
    Row(
        modifier = Modifier
            .padding(16.dp, 0.dp, 16.dp, 0.dp)
            .wrapContentHeight(align = Alignment.CenterVertically)
            .fillMaxWidth()
    ) {
        //DescriptionRating(vm)
        val rate = vm.movieRating.toString()
        val rateNum = rate.replace(',', '.').toFloat()

        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(8.dp))
                .background(
                    when {
                        rateNum >= 9 -> darkGreen
                        rateNum >= 8 -> lightGreen
                        rateNum >= 6 -> yellow
                        rateNum >= 4 -> orange
                        rateNum >= 3 -> fire
                        else -> darkRed
                    }
                )
                .align(Alignment.CenterVertically)
        ) {
            Text(
                modifier = Modifier
                    .padding(14.dp, 2.dp),
                text = rate,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = Color.White
                ),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Название фильма
        Text(
            text = vm.movieName,
            style = TextStyle(
                fontSize = 26.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .widthIn(0.dp, 200.dp)
                .align(Alignment.CenterVertically),
            maxLines = 1,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))

        // Кнопка добавления в избранное
        val isClicked = remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .size(45.dp)
                .align(Alignment.CenterVertically)
                .clip(shape = CircleShape)
                .background(Gray40)
                .clickable(
                    enabled = true,
                    onClick = { isClicked.value = !isClicked.value }
                )
        ) {
            Image(
                painter =
                if (isClicked.value)
                    painterResource(id = R.drawable.red_heart)
                else
                    painterResource(id = R.drawable.heart),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(25.dp)
            )
        }
    }
}

// Описание фильма
@Composable
fun Description(vm: MovieDescriptionViewModel) {
    Text(
        text = vm.description,
        style = TextStyle(
            color = Color.White,
            fontSize = 17.sp
        ),
        maxLines = 4,
        modifier = Modifier
            .padding(16.dp)
    )
}

// Надпись Жанры
@Composable
fun MovieGenres() {
    Text(
        text = "Жанры",
        style = TextStyle(
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        ),
        modifier = Modifier
            .padding(16.dp, 16.dp, 16.dp, 0.dp)
    )
}

// Список жанров фильма
@Composable
fun MovieGenresList(vm: MovieDescriptionViewModel) {
    FlowRow(
        modifier = Modifier
            .padding(16.dp, 10.dp, 16.dp, 0.dp),
    ) {
        repeat(vm.genres.size) { i ->
            MovieGenreLabel(vm.genres[i])
        }
    }
}

// Жанр фильма
@Composable
fun MovieGenreLabel(genre: String) {
    Box (
        modifier = Modifier
            .padding(4.dp, 4.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(AccentColor)
    ) {
        Text(
            text = genre,
            style = TextStyle(
                color = Color.White,
                fontSize = 17.sp
            ),
            modifier = Modifier
                .padding(10.dp, 2.dp)
        )
    }
}

// Надпись О фильме
@Composable
fun AboutMovie() {
    Text(
        text = "О фильме",
        style = TextStyle(
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        ),
        modifier = Modifier
            .padding(16.dp, 16.dp, 16.dp, 0.dp)
    )
}

// Информация о фильме
@Composable
fun MovieInfo(vm: MovieDescriptionViewModel) {

    Column(
        modifier = Modifier
            .padding(16.dp, 10.dp)
    ) {
        InfoRow("Год", "1990")
        InfoRow("Страна", "США, Австралия")
        InfoRow("Слоган", "Добро пожаловать в реальный мир")
        InfoRow("Режиссёр", "Лана Вачовски, Лилли Вачовски")
        InfoRow("Бюджет", "$63 000 000")
        InfoRow("Сборы в мире", "$463 517 383")
        InfoRow("Возраст", "16+")
        InfoRow("Время", "136 мин")
    }
}

// Строка с информацией о фильма
@Composable
fun InfoRow(name: String, description: String) {
    Row(
        modifier = Modifier
            .padding(0.dp, 6.dp)
    ) {
        Text(
            text = name,
            style = TextStyle(
                color = Gray90,
                fontSize = 16.sp
            ),
            modifier = Modifier
                .width(120.dp)
                .widthIn(0.dp, 120.dp)
                .align(Alignment.Top),
            maxLines = 1
        )

        Text(
            text = description,
            style = TextStyle(
                color = Color.White,
                fontSize = 16.sp
            ),
            modifier = Modifier
                .padding(0.dp, 0.dp, 16.dp, 0.dp)
        )
    }
}

// Надпись Отзывы
@Composable
fun FeedbackLable() {
    Row(
        modifier = Modifier
            .padding(16.dp, 8.dp, 16.dp, 0.dp)
    ) {
        Text(
            text = "Отзывы",
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .size(35.dp)
                .align(Alignment.CenterVertically)
                .clip(shape = CircleShape)
                .background(AccentColor)
                .clickable(
                    enabled = true,
                    onClick = { }
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.plus),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(25.dp)
            )
        }
    }
}

// Список отзывов
@Composable
fun FeedbackList(vm: MovieDescriptionViewModel) {
    FeedbackElement(true, false)
    FeedbackElement(false, true)
}

// Элемент списка отзывов
@Composable
fun FeedbackElement(isOurs: Boolean, isAnonimous: Boolean) {
    Column(
        modifier = Modifier
            .padding(16.dp, 10.dp)
    ) {
        FeedbackHeader(isOurs, isAnonimous)

        Text(
            text = "Сразу скажу, что фильм мне понравился. Люблю всех актеров. Все круто, даже слишком!",
            style = TextStyle(
                color = Color.White,
                fontSize = 16.sp
            ),
            modifier = Modifier
                .padding(0.dp, 8.dp, 0.dp, 4.dp)
        )

        Text(
            text = "07.10.2023",
            style = TextStyle(
                color = Gray90,
                fontSize = 14.sp
            )
        )
    }

}

// Заголовок отзыва
@Composable
fun FeedbackHeader(isOurs: Boolean, isAnonimous: Boolean) {
    Row {
        // Аватарка
        Image(
            painter = if (isAnonimous)
                painterResource(id = R.drawable.anonimous)
            else
                painterResource(id = R.drawable.media4),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(45.dp)
                .clip(shape = CircleShape)
        )

        // Имя пользователя
        Column(
            modifier = Modifier
                .padding(10.dp, 0.dp)
                .align(Alignment.CenterVertically),
        ) {
            Text(
                text = if (isAnonimous)
                    "Анонимный пользователь"
                else
                    "Ivan ivan ivan",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )

            if (isOurs) {
                Text(
                    text = "мой отзыв",
                    style = TextStyle(
                        color = Gray90,
                        fontSize = 16.sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        val rateNum = 9f

        // Оценка
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(darkGreen).background(
                    when {
                        rateNum >= 9 -> darkGreen
                        rateNum >= 8 -> lightGreen
                        rateNum >= 6 -> yellow
                        rateNum >= 4 -> orange
                        rateNum >= 3 -> fire
                        else -> darkRed
                    }
                ).padding(8.dp, 2.dp)
                .align(Alignment.CenterVertically)
        ) {
            Image(
                painter = painterResource(id = R.drawable.comment_star),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )

            Text(
                text = rateNum.toInt().toString(),
                style = TextStyle(
                    fontSize = 17.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(4.dp, 0.dp, 0.dp, 0.dp)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Изменение отзыва
        if (isOurs) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.CenterVertically)
                    .clip(shape = CircleShape)
                    .background(Gray40)
                    .clickable(
                        enabled = true,
                        onClick = { }
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.more),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize(0.8f)
                )
            }
        }
    }
}