package com.example.moviesapp2coders.remote

import com.example.moviesapp2coders.domain.Movie
import kotlinx.coroutines.flow.Flow

internal interface Repository {
    suspend fun discoverMovies(page: Int): Pair<Int, List<Movie>>
    suspend fun addFavoriteMovie(movie: Movie)
    val favoriteMovies: Flow<List<Movie>>
}