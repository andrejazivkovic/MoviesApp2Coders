package com.example.moviesapp2coders.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet

@RootNavGraph
@Destination(style = DestinationStyleBottomSheet::class)
@Composable
internal fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    destinationsNavigator: DestinationsNavigator
) {


}