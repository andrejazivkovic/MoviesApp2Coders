package com.example.moviesapp2coders.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
internal interface MovieDao {
    @Upsert
    suspend fun upsertMoviesCollection(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies")
    suspend fun gelAllAvailableMovies(): List<MovieEntity>

    @Query("DELETE FROM movies")
    suspend fun clearMoviesTable()
}
