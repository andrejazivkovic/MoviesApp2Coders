package com.example.moviesapp2coders.util

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.navigation.NavHostController

val LocalCurrentNavController =
    compositionLocalOf<NavHostController> { error("NavController wasn't provided") }
val LocalSnackBarHostState = compositionLocalOf<SnackbarHostState> {
    error("SnackbarHostState wasn't provided")
}

internal fun Modifier.fadingEdge(brush: Brush) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }
