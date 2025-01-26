package com.example.moviesapp2coders.remote

import retrofit2.http.GET
import retrofit2.http.Query

internal interface MoviesApi {
    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("page") page: Int,
    ): MoviesResult

    companion object {
        const val MOVIES_BASE_URL = "https://api.themoviedb.org/3/"
        const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }
}
