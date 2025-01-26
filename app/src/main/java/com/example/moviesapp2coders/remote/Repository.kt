package com.example.moviesapp2coders.remote

import com.example.moviesapp2coders.domain.Movie
import com.example.moviesapp2coders.domain.MovieToggle
import kotlinx.coroutines.flow.Flow

internal interface Repository {
    suspend fun discoverMovies(page: Int): Pair<Int, List<Movie>>
    suspend fun updateMovieFavorite(movie: Movie, isFavorite: Boolean)
    suspend fun searchMovie(movieToggle: MovieToggle, query: String): Flow<Result<List<Movie>>>
    val favoriteMovies: Flow<List<Movie>>
}
