package com.example.mobile_moviescatalog2023.ViewModel

import androidx.lifecycle.ViewModel
import com.example.mobile_moviescatalog2023.R

class MovieDescriptionViewModel: ViewModel() {
    val moviePoster = R.drawable.media1
    val movieRating = 9f
    val movieName = "Матрица"
    val description = "Жизнь Томаса Андерсона разделена надве части: днём он—самый обычный офисный работник, получающий нагоняи от начальства, а ночью превращается в хакера по имени Нео, и нет места в сети, куда он бы не "
    val genres = listOf("боевик", "фантастика", "драма", "мелодрама",  "детектив")
}