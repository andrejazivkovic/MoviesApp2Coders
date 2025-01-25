package com.example.moviesapp2coders.remote

internal data class MoviesResult(
    val page: Int,
    val results: List<MovieDTO>
)
