package com.example.moviesapp2coders.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.moviesapp2coders.R
import com.example.moviesapp2coders.domain.Movie
import com.example.moviesapp2coders.presentation.components.MoviesScaffold
import com.example.moviesapp2coders.util.fadingEdge
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet
import kotlin.math.roundToInt

@RootNavGraph
@Destination(style = DestinationStyleBottomSheet::class)
@Composable
internal fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    movie: Movie,
    destinationsNavigator: DestinationsNavigator
) {
    MoviesScaffold(
        modifier = modifier,
        title = R.string.movie_details,
        onCloseClick = destinationsNavigator::navigateUp
    ) { innerPadding ->
        MovieDetails(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            movie = movie
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun MovieDetails(modifier: Modifier = Modifier, movie: Movie) {
    val bottomFade = remember {
        Brush.verticalGradient(0.30f to Color.Red, 1f to Color.Transparent)
    }
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
        ) {
            GlideImage(
                model = movie.posterPath,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fadingEdge(bottomFade),
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(10.dp)
            ) {
                Text(text = movie.title, fontSize = 25.sp, color = Color.White)
                HearthRating(movie.voteAverage.roundToInt())
            }
            //GENRES
        }
        Text(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp), text = movie.overview)

    }
}

@Composable
private fun HearthRating(rating: Int) {
    val mappedRating = remember { (rating + 1) / 2 }
    Row(verticalAlignment = Alignment.CenterVertically) {
        for (i in 1..5) {
            val icon = if (i <= mappedRating) {
                Icons.Filled.Favorite
            } else {
                Icons.Default.FavoriteBorder
            }
            Icon(
                imageVector = icon,
                contentDescription = "$i Star",
                modifier = Modifier
                    .size(10.dp),
                tint = Color.Red
            )
        }
    }
}
