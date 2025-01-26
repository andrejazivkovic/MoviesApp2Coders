package com.example.moviesapp2coders.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.moviesapp2coders.R
import com.example.moviesapp2coders.remote.Status
import com.example.moviesapp2coders.util.LocalSnackBarHostState

@Composable
internal fun InternetAvailabilitySnackBar(modifier: Modifier = Modifier, status: Status, trigger: Boolean) {
    val snackbarHostState = LocalSnackBarHostState.current
    val noInternetStatus = stringResource(R.string.no_internet_available)
    LaunchedEffect(key1 = status, key2 = trigger) {
        if (status != Status.Available) {
            snackbarHostState.showSnackbar(noInternetStatus)
        }
    }
}
