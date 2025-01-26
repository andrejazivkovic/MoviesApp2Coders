package com.example.moviesapp2coders.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moviesapp2coders.R
import com.example.moviesapp2coders.domain.Movie
import com.example.moviesapp2coders.presentation.MoviesViewModel
import com.example.moviesapp2coders.presentation.components.MovieCard
import com.example.moviesapp2coders.presentation.components.MovieTvShowToggle
import com.example.moviesapp2coders.presentation.components.MoviesScaffold
import com.example.moviesapp2coders.presentation.screens.destinations.MovieDetailsScreenDestination
import com.example.moviesapp2coders.remote.Result
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet

@RootNavGraph
@Destination(style = DestinationStyleBottomSheet::class)
@Composable
internal fun SearchMovieScreen(
    modifier: Modifier = Modifier,
    moviesViewModel: MoviesViewModel = hiltViewModel(),
    destinationsNavigator: DestinationsNavigator
) {
    val searchMovieResult by moviesViewModel.searchResults.collectAsStateWithLifecycle()
    MoviesScaffold(
        modifier = modifier,
        title = R.string.search_movie,
        onCloseClick = destinationsNavigator::navigateUp
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                MovieTvShowToggle(onSelectedOptions = moviesViewModel::updateSelectedSearchOption)
            }
            SearchMovieSection(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                onValueChanged = moviesViewModel::onQueryChanged
            )
            SearchMovieResultSection(
                moviesRes = searchMovieResult,
                onDetailsScreen = {
                    destinationsNavigator.navigate(MovieDetailsScreenDestination(it))
                },
                addToFavorite = {
                    moviesViewModel.updateMovieFavorites(movie = it, favorite = true)
                }
            )
        }
    }
}


@Composable
private fun SearchMovieSection(modifier: Modifier = Modifier, onValueChanged: (String) -> Unit) {
    var selectedOption by remember { mutableStateOf("") }
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        value = selectedOption,
        onValueChange = { newText ->
            selectedOption = newText
            onValueChanged(newText)
        },
        label = {
            Text(
                text = stringResource(R.string.search_movie),
            )

        },
        singleLine = true,
        trailingIcon = {
            Icon(
                modifier = Modifier.padding(end = 3.dp),
                imageVector = Icons.Filled.Search,
                contentDescription = "buttonIcon"
            )
        },
        colors = OutlinedTextFieldDefaults.colors()
            .copy(
                focusedTextColor = MaterialTheme.colorScheme.secondary,
                unfocusedTextColor = MaterialTheme.colorScheme.secondary
            ),
    )
}

@Composable
private fun SearchMovieResultSection(
    modifier: Modifier = Modifier,
    moviesRes: Result<List<Movie>>,
    onDetailsScreen: (Movie) -> Unit,
    addToFavorite: (Movie) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (moviesRes) {
            is Result.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(bottom = 50.dp)
                )
            }

            is Result.Error -> {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(bottom = 50.dp),
                    text = stringResource(R.string.no_movie_like_that),
                    fontSize = 20.sp
                )
            }

            else -> {
                LazyColumn(
                    contentPadding = PaddingValues(5.dp),
                    modifier = modifier
                ) {
                    items((moviesRes as Result.Success).data.size) { movieIndex ->
                        val movie = moviesRes.data[movieIndex]
                        MovieCard(
                            movie = movie,
                            onMoviePicked = onDetailsScreen,
                            addToFavorites = addToFavorite
                        )
                    }
                }
            }
        }
    }
}
