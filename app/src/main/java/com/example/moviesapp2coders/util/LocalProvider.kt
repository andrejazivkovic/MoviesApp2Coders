package com.example.moviesapp2coders.util

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

val LocalCurrentNavController =
    compositionLocalOf<NavHostController> { error("NavController wasn't provided") }