package com.example.moviesapp2coders.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.moviesapp2coders.domain.Movie
import com.example.moviesapp2coders.remote.InternetAvailability
import com.example.moviesapp2coders.remote.Repository
import com.example.moviesapp2coders.remote.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MoviesViewModel @Inject constructor(
    pager: Pager<Int, Movie>,
    internetAvailability: InternetAvailability,
    private val repository: Repository
) : ViewModel() {

    internal val moviesPagerFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map { it }
        }.cachedIn(viewModelScope)

    internal val hasInternet = internetAvailability.internetConnection
        .stateIn(viewModelScope, SharingStarted.Eagerly, Status.Available)

    internal val favoriteMovies = repository.favoriteMovies
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(500), emptyList())

    internal fun addToFavorites(movie: Movie) = viewModelScope.launch {
        repository.addFavoriteMovie(movie)
    }
}
