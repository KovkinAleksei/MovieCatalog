package com.example.mobile_moviescatalog2023.Repository.Movies

import kotlinx.serialization.Serializable

@Serializable
class PageInfo (
    val pageSize: Int,
    val pageCount: Int,
    val currentPage: Int
)