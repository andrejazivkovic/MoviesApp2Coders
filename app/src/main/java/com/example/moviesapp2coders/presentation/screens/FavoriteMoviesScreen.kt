package com.example.moviesapp2coders.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moviesapp2coders.presentation.MoviesViewModel
import com.example.moviesapp2coders.presentation.components.MovieCard
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet

@RootNavGraph
@Destination(style = DestinationStyleBottomSheet::class)
@Composable
internal fun FavoriteMoviesScreen(
    modifier: Modifier = Modifier,
    moviesViewModel: MoviesViewModel = hiltViewModel()
) {
    val favoriteMovies by moviesViewModel.favoriteMovies.collectAsStateWithLifecycle()
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(5.dp),
            modifier = modifier
        ) {
            items(favoriteMovies.size) { movieIndex ->
                val movie = favoriteMovies[movieIndex]
                MovieCard(
                    movie = movie,
                    onMoviePicked = {},
                    addToFavorites = {}
                )
            }
        }
    }
}
