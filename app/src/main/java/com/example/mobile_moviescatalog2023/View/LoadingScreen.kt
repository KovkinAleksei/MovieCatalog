package com.example.mobile_moviescatalog2023.View

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.ui.theme.AccentColor
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.weight(0.2f))

        CircularProgressIndicator(
            color = AccentColor,
            strokeWidth = 4.dp,
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterHorizontally)
        )

        val count = remember { mutableStateOf(0) }

        Text(
            text = stringResource(id = R.string.loaing_in_process) +
                    when (count.value % 4) {
                        0 -> ""
                        1 -> stringResource(id = R.string.single_dot)
                        2 -> stringResource(id = R.string.double_dot)
                        else -> stringResource(id = R.string.triple_dot)
                    },
            style = TextStyle(
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(0.7f))

        LaunchedEffect(Unit) {
            while (true) {
                delay(400)
                count.value++
            }
        }
    }
}

