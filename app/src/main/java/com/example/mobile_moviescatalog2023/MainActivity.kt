package com.example.mobile_moviescatalog2023

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.ui.theme.Accent
import com.example.mobile_moviescatalog2023.ui.theme.MobileMoviesCatalog2023Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileMoviesCatalog2023Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Box(
    ){
        Box(
            modifier = Modifier
                .height(60.dp)
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(1f),
                text = "Fильмус",
                textAlign = TextAlign.Center,
                color = Accent,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )

            Box(
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Image (
                    painter = painterResource(id = R.drawable.back_button),
                    contentDescription = null,
                    modifier = Modifier
                        .size(12.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MobileMoviesCatalog2023Theme {
        Greeting("Android")
    }
}