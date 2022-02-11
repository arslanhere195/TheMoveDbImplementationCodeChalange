package com.dev.androidcodechallange.dataClasses

data class Movie(
    val poster_path: String? = null,
    val original_title: String? = null,
    val release_date: String? = null,
    val id: Int,
    val vote_average: Float
)
