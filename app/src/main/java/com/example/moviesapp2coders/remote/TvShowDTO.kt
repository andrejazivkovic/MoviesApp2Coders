package com.example.moviesapp2coders.remote

internal data class TvShowDTO(
    val id: Int,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Float,
    val poster_path: String?,
    val first_air_date: String,
    val name: String,
    val vote_average: Float,
    val vote_count: Int
)
