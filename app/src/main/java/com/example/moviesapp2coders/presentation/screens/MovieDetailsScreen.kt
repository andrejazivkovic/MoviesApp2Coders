package com.example.moviesapp2coders.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
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
import androidx.compose.ui.text.font.FontWeight
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
        ) {
            MovieDetailsPosterSection(movie = movie)
            MoreInfoSection(modifier = Modifier, movie = movie)
            MovieDescriptionSection(modifier = Modifier, overview = movie.overview)
        }
    }
}

@Composable
private fun MovieDescriptionSection(modifier: Modifier = Modifier, overview: String) =
    Box(modifier = modifier.padding(16.dp)) {
        Text(
            text = overview,
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = FontWeight.Thin
        )
    }

@Composable
private fun MoreInfoSectionItem(modifier: Modifier = Modifier, text: String) =
    OutlinedButton(
        modifier = modifier.padding(end = 10.dp),
        border = BorderStroke(1.dp, Color.White),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent),
        onClick = {},
    ) {
        Text(text = text, color = Color.White)
    }

@Composable
private fun MoreInfoSection(modifier: Modifier = Modifier, movie: Movie) = Row(
    modifier = modifier
        .fillMaxWidth()
        .padding(16.dp)
        .horizontalScroll(rememberScrollState()),
) {
    MoreInfoSectionItem(text = movie.releaseDate)
    MoreInfoSectionItem(text = movie.originalTitle)
    MoreInfoSectionItem(text = movie.originalLanguage)
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun MovieDetailsPosterSection(modifier: Modifier = Modifier, movie: Movie) {
    val bottomFade = remember {
        Brush.verticalGradient(0.60f to Color.Red, 1f to Color.Transparent)
    }
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        GlideImage(
            modifier = Modifier
                .fadingEdge(bottomFade),
            model = movie.posterPath,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(10.dp)
        ) {
            Text(
                text = movie.title,
                fontSize = 25.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            HearthRating(movie)
        }
    }
}

@Composable
private fun HearthRating(movie: Movie) {
    val mappedRating = remember { (movie.voteAverage + 1) / 2 }
    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
        Text(
            modifier = Modifier.padding(end = 3.dp),
            text = "Total votes:${movie.voteCount} |",
            fontSize = 17.sp,
            color = Color.White,
            fontWeight = FontWeight.Thin
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            for (i in 1..5) {
                val icon = if (i <= mappedRating) {
                    Icons.Filled.Favorite
                } else {
                    Icons.Default.FavoriteBorder
                }
                Icon(
                    imageVector = icon,
                    contentDescription = "rating",
                    modifier = Modifier
                        .size(19.dp),
                    tint = Color.Red
                )
            }
        }
    }
}
