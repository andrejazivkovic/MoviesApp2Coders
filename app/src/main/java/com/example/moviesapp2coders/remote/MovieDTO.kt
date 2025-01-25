package com.example.moviesapp2coders.remote

internal data class MovieDTO(
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Float,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Float,
    val vote_count: Int
)
