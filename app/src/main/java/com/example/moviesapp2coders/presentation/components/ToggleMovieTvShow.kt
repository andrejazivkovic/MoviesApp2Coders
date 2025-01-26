package com.example.moviesapp2coders.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviesapp2coders.domain.MovieToggle
import com.example.moviesapp2coders.ui.theme.PremiumGreen

@Composable
internal fun MovieTvShowToggle(
    modifier: Modifier = Modifier,
    onSelectedOptions: (MovieToggle) -> Unit
) {
    var selectedOption by remember { mutableStateOf<MovieToggle>(MovieToggle.MOVIE) }

    Row(
        modifier = modifier
            .width(200.dp)
            .height(50.dp)
            .padding(2.dp)
            .animateContentSize()
            .background(Color.Transparent, RoundedCornerShape(25.dp))
            .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(25.dp)),
        horizontalArrangement = Arrangement.Center
    ) {
        ToggleButton(
            modifier = Modifier.weight(1f),
            text = stringResource(MovieToggle.MOVIE.name),
            isSelected = selectedOption == MovieToggle.MOVIE,
            onClick = {
                selectedOption = MovieToggle.MOVIE
                onSelectedOptions(selectedOption)
            }
        )

        Spacer(modifier = Modifier.width(4.dp))

        ToggleButton(
            modifier = Modifier.weight(1f),
            text = stringResource(MovieToggle.TV_SHOW.name),
            isSelected = selectedOption == MovieToggle.TV_SHOW,
            onClick = {
                selectedOption = MovieToggle.TV_SHOW
                onSelectedOptions(selectedOption)
            }
        )
    }
}

@Composable
fun ToggleButton(modifier: Modifier, text: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = modifier
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(25.dp),
        color = if (isSelected) PremiumGreen else Color.Transparent
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            color = if (isSelected) Color.Black else Color.Gray,
            modifier = Modifier.padding(12.dp),
        )
    }
}
