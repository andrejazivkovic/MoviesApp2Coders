package com.example.moviesapp2coders.presentation.components

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.moviesapp2coders.R
import com.example.moviesapp2coders.presentation.screens.destinations.FavoriteMoviesScreenDestination
import com.example.moviesapp2coders.presentation.screens.destinations.MainMovieScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

//Bottom navigation enums representing each a destination screen
enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
    @StringRes val label: Int
) {
    Main(MainMovieScreenDestination, Icons.Default.Home, R.string.main_movie_screen),
    Favorite(FavoriteMoviesScreenDestination, Icons.Default.Lock, R.string.favorite_screen)
}
