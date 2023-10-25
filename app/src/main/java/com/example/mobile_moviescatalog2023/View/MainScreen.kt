package com.example.mobile_moviescatalog2023.View

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.mobile_moviescatalog2023.R
import kotlinx.coroutines.delay
import okhttp3.internal.userAgent

@Composable
fun MainScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            FilmsPager()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilmsPager() {
    val medialList = listOf( R.drawable.media1, R.drawable.media2, R.drawable.media3, R.drawable.media4)

    val pagerState = rememberPagerState()
    var currentPage = remember { mutableStateOf(0) }

    // Обновление состояния HorizontalPager с определенным интервалом времени
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            currentPage.value = (currentPage.value + 1) % 4
            pagerState.animateScrollToPage((currentPage.value))
        }
    }

    Column (
        modifier = Modifier
            .fillMaxWidth()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(0.72f),
            pageCount = 4

        ) {
            page -> Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = medialList[page]),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    MainScreen()
}