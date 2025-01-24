package com.example.moviesapp2coders.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MovieEntity::class],
    version = 1
)
internal abstract class MoviesDatabase : RoomDatabase() {
    internal abstract val movieDao: MovieDao
}
