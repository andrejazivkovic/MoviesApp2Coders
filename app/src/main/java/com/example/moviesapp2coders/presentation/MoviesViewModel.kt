package com.example.moviesapp2coders.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.moviesapp2coders.domain.Movie
import com.example.moviesapp2coders.domain.MovieToggle
import com.example.moviesapp2coders.remote.InternetAvailability
import com.example.moviesapp2coders.remote.Repository
import com.example.moviesapp2coders.remote.Result
import com.example.moviesapp2coders.remote.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
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

    internal fun updateMovieFavorites(movie: Movie, favorite: Boolean) = viewModelScope.launch {
        repository.updateMovieFavorite(movie, favorite)
    }

    private val selectedSearchOptionUrlMutable = MutableStateFlow<MovieToggle>(MovieToggle.MOVIE)
    private val selectedSearchOptionUrl = selectedSearchOptionUrlMutable.asStateFlow()

    internal fun updateSelectedSearchOption(movieToggle: MovieToggle) {
        selectedSearchOptionUrlMutable.value = movieToggle
    }

    private val searchQueryMutable = MutableStateFlow("")

    //Throttling user keyboard interaction for 2second on every tap
    //flatMapping the previous flow and setting new flow from repository
    internal val searchResults = searchQueryMutable
        .debounce(2000)
        .filter { it.isNotBlank() }
        .flatMapLatest { query ->
            repository.searchMovie(movieToggle = selectedSearchOptionUrl.value, query = query)
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, Result.Success(emptyList()))

    internal fun onQueryChanged(query: String) {
        searchQueryMutable.value = query
    }
}
