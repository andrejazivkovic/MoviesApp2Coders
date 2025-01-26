package com.example.moviesapp2coders.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
internal interface MovieDao {
    @Upsert
    suspend fun upsertMoviesCollection(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies")
    suspend fun gelAllAvailableMovies(): List<MovieEntity>

    @Query("DELETE FROM movies WHERE isFavorite = 0")
    suspend fun clearMoviesTable()

    @Query("SELECT * FROM movies WHERE isFavorite = 1")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>

    @Query("DELETE FROM movies WHERE id = :movieId")
    suspend fun deleteMovie(movieId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)
}
