package com.example.moviesapp2coders.remote

import com.example.moviesapp2coders.domain.Movie

internal interface Repository {
    suspend fun discoverMovies(page:Int) : Pair<Int, List<Movie>>
}