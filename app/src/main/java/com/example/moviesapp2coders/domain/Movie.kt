package com.example.moviesapp2coders.domain

import androidx.compose.runtime.Stable

@Stable
internal data class Movie(
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Float,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Float,
    val voteCount: Int,
    val isFavorite: Boolean
)
