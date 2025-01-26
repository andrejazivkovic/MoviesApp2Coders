package com.example.moviesapp2coders.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
internal fun MovieCard(
    modifier: Modifier = Modifier,
    movie: Movie,
    onMoviePicked: () -> Unit,
    addToFavorites: () -> Unit
) {
    val bottomFade = remember {
        Brush.verticalGradient(0.65f to Color.Red, 1f to Color.Transparent)
    }
    Card(
        modifier = modifier
            .padding(15.dp)
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
                model = movie.posterPath,
                contentDescription = movie.title,
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
                    description = movie.overview
                )
                ButtonsSection(onMoviePicked = onMoviePicked, addToFavorites = addToFavorites)
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
    onMoviePicked: () -> Unit,
    addToFavorites: () -> Unit
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
        imageVector = Icons.Default.Add,
        text = R.string.movie_favorites,
        onClick = addToFavorites
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
