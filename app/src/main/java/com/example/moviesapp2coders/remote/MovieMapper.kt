package com.example.moviesapp2coders.remote

import com.example.moviesapp2coders.domain.Movie
import com.example.moviesapp2coders.local.MovieEntity
import com.example.moviesapp2coders.remote.MoviesApi.Companion.BASE_IMAGE_URL

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
    posterPath = "$BASE_IMAGE_URL$posterPath",
    releaseDate = releaseDate,
    title = title,
    voteAverage = voteAverage,
    voteCount = voteCount,
    isFavorite = isFavorite
)

internal fun Movie.toMovieEntity(isFavorite: Boolean = false) = MovieEntity(
    id = id,
    originalTitle = originalTitle,
    overview = overview,
    originalLanguage = originalLanguage,
    popularity = popularity,
    posterPath = "$BASE_IMAGE_URL$posterPath",
    releaseDate = releaseDate,
    title = title,
    voteAverage = voteAverage,
    voteCount = voteCount,
    isFavorite = isFavorite
)

internal fun MovieDTO.toMovie() = Movie(
    id = id,
    originalTitle = original_title,
    originalLanguage = original_language,
    overview = overview,
    popularity = popularity,
    posterPath = "$BASE_IMAGE_URL$poster_path",
    releaseDate = release_date,
    title = title,
    voteAverage = vote_average,
    voteCount = vote_count,
    isFavorite = false
)

internal fun TvShowDTO.toMovie() = Movie(
    id = id,
    originalTitle = original_name,
    originalLanguage = original_language,
    overview = overview,
    popularity = popularity,
    posterPath = "$BASE_IMAGE_URL$poster_path",
    releaseDate = first_air_date,
    title = name,
    voteAverage = vote_average,
    voteCount = vote_count,
    isFavorite = false
)
