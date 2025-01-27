package com.example.moviesapp2coders.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.moviesapp2coders.R
import com.example.moviesapp2coders.domain.Movie
import com.example.moviesapp2coders.ui.theme.TransparentGrey
import com.example.moviesapp2coders.util.fadingEdge

// Movie Card that displays short description and two buttons for adding to favorites and navigating
// to movie details screen
@Composable
internal fun MovieCard(
    modifier: Modifier = Modifier,
    movie: Movie,
    onMoviePicked: (Movie) -> Unit,
    addToFavorites: (Movie) -> Unit = {},
    removeFavorites: (Movie) -> Unit = {}
) {
    //Added is favorite so can manage ui difference for add/remove section of the card
    var isFavorite by rememberSaveable(movie) { mutableStateOf(!movie.isFavorite) }
    MovieCardContent(
        modifier = modifier,
        posterPath = movie.posterPath,
        title = movie.title,
        overview = movie.overview,
        isFavorite = isFavorite,
        onMoviePicked = {
            onMoviePicked(movie)
        },
        addToFavorites = {
            addToFavorites(movie)
            isFavorite = !isFavorite
        },
        removeFavorites = {
            removeFavorites(movie)
            isFavorite = !isFavorite
        }
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun MovieCardContent(
    modifier: Modifier = Modifier,
    posterPath: String?,
    title: String,
    overview: String,
    isFavorite: Boolean,
    onMoviePicked: () -> Unit,
    addToFavorites: () -> Unit = {},
    removeFavorites: () -> Unit = {}
) {
    val bottomFade = remember {
        Brush.verticalGradient(0.65f to Color.Red, 1f to Color.Transparent)
    }
    Card(
        modifier = modifier
            .padding(15.dp)
            .clickable(onClick = onMoviePicked)
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(8.dp)
            ),
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            GlideImage(
                model = posterPath,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(0.70f)
                    .fadingEdge(bottomFade),
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
            ) {
                MovieDescription(
                    modifier = Modifier.padding(horizontal = 15.dp),
                    description = overview
                )
                ButtonsSection(
                    isMovieFavorite = isFavorite,
                    onMoviePicked = onMoviePicked,
                    addToFavorites = addToFavorites,
                    removeFavorite = removeFavorites
                )
            }
        }
    }
}

@Composable
private fun MovieDescription(modifier: Modifier = Modifier, description: String) = Text(
    modifier = modifier
        .background(TransparentGrey)
        .clip(RoundedCornerShape(4.dp)),
    text = description,
    style = MaterialTheme.typography.body2,
    color = Color.White,
    maxLines = 2,
    overflow = TextOverflow.Ellipsis
)

@Composable
private fun ButtonsSection(
    modifier: Modifier = Modifier,
    isMovieFavorite: Boolean,
    onMoviePicked: () -> Unit,
    addToFavorites: () -> Unit,
    removeFavorite: () -> Unit
) = Row(
    modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 15.dp, vertical = 15.dp),
    horizontalArrangement = Arrangement.SpaceEvenly,
    verticalAlignment = Alignment.CenterVertically
) {
    MovieButton(
        modifier = Modifier.weight(1f),
        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
        text = R.string.movie_info,
        onClick = onMoviePicked
    )
    Spacer(modifier = Modifier.size(15.dp))
    MovieButton(
        modifier = Modifier.weight(1f),
        imageVector = if (isMovieFavorite) Icons.Default.Add else Icons.Default.Clear,
        text = if (isMovieFavorite) R.string.movie_favorites else R.string.remove_favorite,
        onClick = if (isMovieFavorite) addToFavorites else removeFavorite
    )
}


@Composable
private fun MovieButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    @StringRes text: Int,
    onClick: () -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = TransparentGrey)
    ) {
        Icon(
            modifier = Modifier.padding(end = 3.dp),
            imageVector = imageVector,
            contentDescription = "buttonIcon"
        )
        Text(stringResource(text), color = Color.White)
    }
}
