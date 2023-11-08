@file:OptIn(ExperimentalLayoutApi::class)

package com.example.mobile_moviescatalog2023.View

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import coil.compose.AsyncImage
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.Repository.MovieDetails.ReviewDetails
import com.example.mobile_moviescatalog2023.ViewModel.MovieDescriptionViewModel
import com.example.mobile_moviescatalog2023.ui.theme.*

// Экран с описанием фильма
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieDescriptonScreen(id: String?, onBackButtonClick: () -> Unit) {
    val vm: MovieDescriptionViewModel = viewModel()
    val isLoaded = remember{ mutableStateOf(false) }

    if (!vm.isInitialized)
        vm.getMovieDetails(id, isLoaded)

    if (isLoaded.value) {
        Scaffold(
            topBar = {
                TopBar(onBackButtonClick)
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
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

// Верхняя часть экрана
@Composable
fun TopBar(onBackButtonClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(16.dp, 10.dp, 0.dp, 10.dp)
            .fillMaxWidth()
    ) {
        BackButton {
            onBackButtonClick()
        }
    }
}

// Постер с фильмом
@Composable
fun MoviePoster(vm: MovieDescriptionViewModel) {
    Box (){
        val density = LocalDensity.current.density

        AsyncImage(
            model = vm.moviePoster.value,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.725f),
            contentScale = ContentScale.Crop
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
        val rate = vm.movieRating.value
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
            text = vm.movieName.value,
            style = TextStyle(
                fontSize = 26.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .widthIn(0.dp, 200.dp)
                .align(Alignment.CenterVertically),
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
        text = vm.description.value,
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
        vm.genres.value?.let {
            repeat(it.size) { i ->
                if (vm.genres.value != null)
                    MovieGenreLabel(vm.genres.value!![i].name)
            }
        }
    }
}

// Жанр фильма
@Composable
fun MovieGenreLabel(genre: String) {
    Box (
        modifier = Modifier
            .padding(0.dp, 4.dp, 4.dp, 4.dp)
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
        InfoRow("Год", vm.year.value.toString().replace("-1", ""))
        InfoRow("Страна", vm.country.value)
        InfoRow("Слоган", vm.slogan.value)
        InfoRow("Режиссёр", vm.director.value)
        InfoRow("Бюджет", vm.budget.value)
        InfoRow("Сборы в мире", vm.fees.value)
        InfoRow("Возраст", vm.ageLimit.value)
        InfoRow("Время", vm.time.value)
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
    repeat(vm.reviews.value?.size ?: 0) { i ->
        FeedbackElement(vm.reviews.value?.get(i))
    }
}

// Элемент списка отзывов
@Composable
fun FeedbackElement(review: ReviewDetails?) {
    if (review == null)
        return

    Column(
        modifier = Modifier
            .padding(16.dp, 10.dp, 8.dp, 0.dp)
    ) {
        FeedbackHeader(review)

        Text(
            text = review.reviewText ?: "",
            style = TextStyle(
                color = Color.White,
                fontSize = 16.sp
            ),
            modifier = Modifier
                .padding(0.dp, 8.dp, 0.dp, 4.dp)
        )

        val day = review.createDateTime.slice(8..9)
        val month = review.createDateTime.slice(5..6)
        val year = review.createDateTime.slice(0..3)

        Text(
            text = "${day}.${month}.${year}",
            style = TextStyle(
                color = Gray90,
                fontSize = 14.sp
            )
        )
    }
}

// Заголовок отзыва
@Composable
fun FeedbackHeader(review: ReviewDetails) {
    val isOurs = false

    Row {
        // Аватарка
        val avatar = review.author.avatar

        if (avatar != null && !review.isAnonymous) {
            AsyncImage(
                model = avatar,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(45.dp)
                    .clip(shape = CircleShape)
            )
        }
        else {
            Image(
                painter = painterResource(id = R.drawable.anonimous),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(45.dp)
                    .clip(shape = CircleShape)
            )
        }

        // Имя пользователя
        Column(
            modifier = Modifier
                .padding(10.dp, 0.dp)
                .align(Alignment.CenterVertically),
        ) {
            Text(
                text = if (review.isAnonymous)
                    "Анонимный пользователь"
                else
                    review.author.nickName ?: "Анонимный пользователь",
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

        val rateNum = review.rating

        // Оценка
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(darkGreen)
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
                .padding(8.dp, 2.dp)
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