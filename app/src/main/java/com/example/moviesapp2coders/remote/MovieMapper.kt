package com.example.moviesapp2coders.remote

import com.example.moviesapp2coders.domain.Movie
import com.example.moviesapp2coders.local.MovieEntity

internal fun MovieDTO.toMovieEntity() = MovieEntity(id = id)

internal fun MovieEntity.toMovie() = Movie(id = id)
