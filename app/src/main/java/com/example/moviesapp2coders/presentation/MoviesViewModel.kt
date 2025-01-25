package com.example.moviesapp2coders.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.moviesapp2coders.domain.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
internal class MoviesViewModel @Inject constructor(
    pager: Pager<Int, Movie>
) : ViewModel() {

    internal val moviesPagerFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map { it }
        }.cachedIn(viewModelScope)

}
