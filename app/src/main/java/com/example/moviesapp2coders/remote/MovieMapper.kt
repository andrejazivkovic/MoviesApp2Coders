package com.example.moviesapp2coders.remote

import com.example.moviesapp2coders.domain.Movie
import com.example.moviesapp2coders.local.MovieEntity

internal fun MovieDTO.toMovieEntity() = MovieEntity(
    id = id,
    originalTitle = original_title,
    originalLanguage = original_language,
    overview = overview,
    popularity = popularity,
    posterPath = poster_path,
    releaseDate = release_date,
    title = title,
    voteAverage = vote_average,
    voteCount = vote_count
)

internal fun MovieEntity.toMovie() = Movie(
    id = id,
    originalTitle = originalTitle,
    overview = overview,
    originalLanguage = originalLanguage,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    voteAverage = voteAverage,
    voteCount = voteCount
)
