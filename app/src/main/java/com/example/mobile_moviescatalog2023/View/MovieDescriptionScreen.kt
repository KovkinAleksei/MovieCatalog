@file:OptIn(ExperimentalLayoutApi::class)

package com.example.mobile_moviescatalog2023.View

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.Repository.MovieDetails.ReviewDetails
import com.example.mobile_moviescatalog2023.ViewModel.MovieDescriptionViewModel
import com.example.mobile_moviescatalog2023.ui.theme.*

// Экран с описанием фильма
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieDescriptonScreen(
    id: String?,
    navController: NavController,
    onBackButtonClick: () -> Unit
) {
    val vm: MovieDescriptionViewModel = viewModel()
    navController.currentBackStackEntry?.savedStateHandle?.set(isBack, true)

    val isLoaded = remember { mutableStateOf(false) }
    val isConnected = remember { mutableStateOf(true) }

    if (!vm.isInitialized.value) {
        isLoaded.value = false
        vm.getMovieDetails(id, isLoaded, isConnected)
    }

    if (isLoaded.value) {
        val listState = rememberLazyListState()
        val isScrolled = remember { mutableStateOf(false) }

        Scaffold(
            topBar = {
                Column {
                    TopBar(onBackButtonClick, vm, isScrolled.value)

                    if (isScrolled.value) {
                        Divider(
                            modifier = Modifier
                                .height(1.dp)
                                .background(Gray40)
                        )
                    }
                }
            }
        ) {
            LazyColumn(
                state = listState
            ) {
                item {
                    MoviePoster(vm, listState)
                }
                item {
                    MovieHeader(vm)
                }
                item {
                    Description(vm)
                    MovieGenres()
                    MovieGenresList(vm)
                    AboutMovie()
                    MovieInfo(vm)
                    FeedbackLable(vm)
                    FeedbackList(vm)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }

        LaunchedEffect(listState) {
            snapshotFlow { listState.firstVisibleItemIndex }
                .collect { index ->
                    isScrolled.value = index > 1
                }
        }

        if (vm.isEditingFeedback.value)
            FeedbackDialog(vm)
    } else if (!isConnected.value) {
        TopBar(onBackButtonClick, vm, false)
        ConnectionErrorScreen()
    } else {
        LoadingScreen()
    }
}

// Верхняя часть экрана
@Composable
fun TopBar(onBackButtonClick: () -> Unit, vm: MovieDescriptionViewModel, isScrolled: Boolean) {
    Row(
        modifier = Modifier
            .padding(16.dp, 0.dp, 16.dp, 0.dp)
            .height(if (isScrolled) 49.dp else 50.dp)
            .fillMaxWidth()
    ) {
        Image(
            modifier = Modifier
                .height(30.dp)
                .size(7.dp)
                .width(40.dp)
                .align(Alignment.CenterVertically)
                .clickable(
                    enabled = true,
                    onClick = { onBackButtonClick() }
                ),
            imageVector = ImageVector.vectorResource(id = R.drawable.back_arrow),
            contentDescription = null
        )

        if (isScrolled) {
            // Название фильма
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = vm.movieName.value,
                style = TextStyle(
                    fontSize = 26.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 1,
                modifier = Modifier
                    .widthIn(0.dp, 200.dp)
                    .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))

            // Кнопка добавления в избранное / удаления из избранных
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterVertically)
                    .clip(shape = CircleShape)
                    .background(Gray40)
                    .clickable(
                        enabled = true,
                        onClick = {
                            if (!vm.isFavorite.value)
                                vm.addToFavourites()
                            else
                                vm.deleteFromFavourites()
                        }
                    )
            ) {
                Image(
                    imageVector = if (vm.isFavorite.value)
                        ImageVector.vectorResource(id = R.drawable.red_heart)
                    else
                        ImageVector.vectorResource(id = R.drawable.heart),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(25.dp)
                )
            }
        }
    }
}

// Постер с фильмом
@Composable
fun MoviePoster(vm: MovieDescriptionViewModel, listState: LazyListState) {
    val density = LocalDensity.current.density

    Box(
        modifier = Modifier
            .graphicsLayer {
                alpha = 1 - (1f / (500 * density) * listState.firstVisibleItemScrollOffset)
            }
    ) {
        AsyncImage(
            model = vm.moviePoster.value,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp),
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

        // Кнопка добавления в избранное / удаления из избранных
        Box(
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.CenterVertically)
                .clip(shape = CircleShape)
                .background(Gray40)
                .clickable(
                    enabled = true,
                    onClick = {
                        if (!vm.isFavorite.value)
                            vm.addToFavourites()
                        else
                            vm.deleteFromFavourites()
                    }
                )
        ) {
            Image(
                imageVector = if (vm.isFavorite.value)
                    ImageVector.vectorResource(id = R.drawable.red_heart)
                else
                    ImageVector.vectorResource(id = R.drawable.heart),
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
    val more = remember { mutableStateOf(false) }

    if (more.value) {
        Column {
            Text(
                text = vm.description.value,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 17.sp
                ),
                modifier = Modifier
                    .padding(16.dp, 16.dp, 16.dp, 0.dp)
            )

            Row(
                modifier = Modifier
                    .padding(16.dp, 0.dp)
                    .clickable(
                        enabled = true,
                        onClick = {
                            more.value = false
                        }
                    ),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    text = stringResource(id = R.string.more),
                    style = TextStyle(
                        color = AccentColor,
                        fontSize = 17.sp
                    )
                )

                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.up_arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(10.dp),
                )
            }
        }
    } else {
        Column {
            Box {
                Text(
                    text = vm.description.value,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 17.sp
                    ),
                    maxLines = 2,
                    modifier = Modifier
                        .padding(16.dp, 16.dp, 16.dp, 0.dp)
                )

                val density = LocalDensity.current.density

                Box(
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color.Transparent, DarkGray700),
                                startY = 0f * density,
                                endY = 60f * density
                            )
                        )
                )
            }

            Row(
                modifier = Modifier
                    .padding(16.dp, 0.dp)
                    .clickable(
                        enabled = true,
                        onClick = {
                            more.value = true
                        }
                    ),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    text = stringResource(id = R.string.more),
                    style = TextStyle(
                        color = AccentColor,
                        fontSize = 17.sp
                    )
                )

                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.down_arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(10.dp),
                )
            }
        }
    }
}

// Надпись Жанры
@Composable
fun MovieGenres() {
    Text(
        text = stringResource(id = R.string.genres),
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
    Box(
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
        text = stringResource(id = R.string.about_movie),
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
        InfoRow(stringResource(id = R.string.year), vm.year.value.toString().replace("-1", ""))
        InfoRow(stringResource(id = R.string.country), vm.country.value)
        InfoRow(stringResource(id = R.string.slogan), vm.slogan.value)
        InfoRow(stringResource(id = R.string.director), vm.director.value)
        InfoRow(stringResource(id = R.string.budget), vm.budget.value)
        InfoRow(stringResource(id = R.string.fees), vm.fees.value)
        InfoRow(stringResource(id = R.string.age), vm.ageLimit.value)
        InfoRow(stringResource(id = R.string.time), vm.time.value)
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
fun FeedbackLable(vm: MovieDescriptionViewModel) {
    Row(
        modifier = Modifier
            .padding(16.dp, 8.dp, 16.dp, 0.dp)
    ) {
        Text(
            text = stringResource(id = R.string.reviews),
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )

        Spacer(modifier = Modifier.weight(1f))

        val feedbackIsLeft = vm.addButtonIsHidden

        if (!feedbackIsLeft.value) {
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .align(Alignment.CenterVertically)
                    .clip(shape = CircleShape)
                    .background(AccentColor)
                    .clickable(
                        enabled = true,
                        onClick = {
                            vm.editFeedback()
                        }
                    )
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.plus),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(25.dp)
                )
            }
        }
    }
}

// Список отзывов
@Composable
fun FeedbackList(vm: MovieDescriptionViewModel) {
    FeedbackElement(
        review = vm.reviews.value?.find { it ->
            vm.isOursFeedback(it)
        }, vm = vm
    )

    repeat(vm.reviews.value?.size ?: 0) { i ->
        val review = vm.reviews.value?.get(i)

        if (!vm.isOursFeedback(review))
            FeedbackElement(vm.reviews.value?.get(i), vm)
    }
}

// Элемент списка отзывов
@Composable
fun FeedbackElement(review: ReviewDetails?, vm: MovieDescriptionViewModel) {
    if (review == null)
        return

    Column(
        modifier = Modifier
            .padding(16.dp, 10.dp, 16.dp, 0.dp)
    ) {
        FeedbackHeader(review, vm)
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column {
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

            if (vm.isShowingOptions.value && vm.isOursFeedback(review)) {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .width(170.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Gray40)
                ) {
                    Row(
                        modifier = Modifier
                            .height(40.dp)
                            .padding(8.dp, 0.dp)
                            .clickable(
                                enabled = true,
                                onClick = {
                                    vm.editFeedback()
                                }
                            )
                    ) {
                        Text(
                            text = stringResource(id = R.string.edit),
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 14.sp
                            ),
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )

                        Spacer(Modifier.weight(1f))

                        Image(
                            imageVector = ImageVector.vectorResource(id = R.drawable.pencil),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .size(25.dp)
                        )
                    }

                    Divider(
                        modifier = Modifier
                            .height(1.dp)
                            .background(Gray54)
                    )

                    Row(
                        modifier = Modifier
                            .height(40.dp)
                            .padding(8.dp, 0.dp)
                            .clickable(
                                enabled = true,
                                onClick = {
                                    vm.deleteReview()
                                }
                            )
                    ) {
                        Text(
                            text = stringResource(id = R.string.delete),
                            style = TextStyle(
                                color = darkRed,
                                fontSize = 14.sp
                            ),
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )

                        Spacer(Modifier.weight(1f))

                        Image(
                            imageVector = ImageVector.vectorResource(id = R.drawable.delete_bin),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .size(25.dp)
                        )
                    }
                }
            }
        }
    }
}

// Заголовок отзыва
@Composable
fun FeedbackHeader(review: ReviewDetails, vm: MovieDescriptionViewModel) {
    val isOurs = vm.isOursFeedback(review)

    Row {
        // Аватарка
        val avatar = review.author?.avatar

        if (avatar != null && !review.isAnonymous) {
            AsyncImage(
                model = avatar,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(45.dp)
                    .clip(shape = CircleShape)
            )
        } else {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.anonymous),
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
                    stringResource(id = R.string.anonymous_user)
                else
                    review.author?.nickName ?: stringResource(id = R.string.anonymous_user),
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )

            if (isOurs) {
                Text(
                    text = stringResource(id = R.string.my_review),
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
                imageVector = ImageVector.vectorResource(id = R.drawable.comment_star),
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

        // Изменение отзыва
        if (isOurs) {
            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.CenterVertically)
                    .clip(shape = CircleShape)
                    .background(Gray40)
                    .clickable(
                        enabled = true,
                        onClick = {
                            vm.openOptions()
                        }
                    )
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.more),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize(0.8f)
                )
            }
        }
    }
}