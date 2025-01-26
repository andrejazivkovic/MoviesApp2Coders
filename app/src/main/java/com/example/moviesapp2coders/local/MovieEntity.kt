package com.example.moviesapp2coders.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
internal data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val originalLanguage:String,
    val originalTitle:String,
    val overview:String,
    val popularity:Float,
    val posterPath:String,
    val releaseDate:String,
    val title: String,
    val voteAverage:Float,
    val voteCount: Int,
    val isFavorite: Boolean = false
)
