package com.example.moviesapp2coders.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moviesapp2coders.R
import com.example.moviesapp2coders.domain.Movie
import com.example.moviesapp2coders.presentation.MoviesViewModel
import com.example.moviesapp2coders.presentation.components.MovieCard
import com.example.moviesapp2coders.presentation.components.MoviesScaffold
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet

//Favorite movie screen with horizontal scroll, user can navigate to movie detail or remove it
// from favorites
//Animated visibility for adding message if collection is empty
@RootNavGraph
@Destination(style = DestinationStyleBottomSheet::class)
@Composable
internal fun FavoriteMoviesScreen(
    modifier: Modifier = Modifier,
    moviesViewModel: MoviesViewModel = hiltViewModel(),
    destinationsNavigator: DestinationsNavigator
) {
    val favoriteMovies by moviesViewModel.favoriteMovies.collectAsStateWithLifecycle()
    MoviesScaffold(
        modifier = modifier,
        title = R.string.watch_later,
        onCloseClick = destinationsNavigator::navigateUp
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            FavoriteMoviesSection(favoriteMovies = favoriteMovies) {
                moviesViewModel.updateMovieFavorites(movie = it, favorite = false)
            }
            EmptyWatchLatterList(favoriteMovies = favoriteMovies)
        }
    }
}

@Composable
private fun FavoriteMoviesSection(
    modifier: Modifier = Modifier,
    favoriteMovies: List<Movie>,
    onRemoveFavoriteMovie: (Movie) -> Unit
) {
    AnimatedVisibility(favoriteMovies.isNotEmpty(), modifier = modifier) {
        LazyRow(
            contentPadding = PaddingValues(bottom = 150.dp),
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(favoriteMovies.size) { movieIndex ->
                val movie = favoriteMovies[movieIndex]
                MovieCard(
                    modifier = modifier
                        .height(400.dp)
                        .width(310.dp),
                    movie = movie,
                    onMoviePicked = {

                    },
                    removeFavorites = onRemoveFavoriteMovie
                )
            }
        }
    }
}

@Composable
private fun EmptyWatchLatterList(modifier: Modifier = Modifier, favoriteMovies: List<Movie>) =
    AnimatedVisibility(favoriteMovies.isEmpty(), modifier = modifier) {
        Box(modifier = Modifier.fillMaxSize().padding(bottom = 150.dp)) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = stringResource(R.string.empty_favorite_movies),
                fontSize = 30.sp,
                color = Color.White
            )
        }
    }
