package com.example.moviesapp2coders.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.moviesapp2coders.domain.Movie
import com.example.moviesapp2coders.presentation.MoviesViewModel
import com.example.moviesapp2coders.presentation.components.MovieCard
import com.example.moviesapp2coders.util.fadingEdge
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
internal fun MainMovieScreen(
    modifier: Modifier = Modifier,
    moviesViewModel: MoviesViewModel = hiltViewModel(),
    destinationsNavigator: DestinationsNavigator
) {
    val movies = moviesViewModel.moviesPagerFlow.collectAsLazyPagingItems()
    MoviesListRepresentation(movies = movies) {

    }
}

@Composable
private fun MoviesListRepresentation(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<Movie>,
    onMovieClicked: () -> Unit
) {

    Box(modifier = Modifier.fillMaxSize()) {
        if (movies.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyColumn (
                contentPadding = PaddingValues(5.dp),
                modifier = modifier
            ) {
                items(movies.itemCount) { movieIndex ->
                    val movie = movies[movieIndex]
                    if (movie != null) {
                        MovieCard(movie = movie, onMoviePicked = {}, addToFavorites = {})
                    }
                }
                item {
                    if (movies.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

