package com.example.moviesapp2coders.domain

import androidx.annotation.StringRes
import com.example.moviesapp2coders.R

internal sealed class MovieToggle(@StringRes val name: Int) {
    data object MOVIE : MovieToggle(name = R.string.main_movie_screen)
    data object TV_SHOW : MovieToggle(name = R.string.tv_shows)
}