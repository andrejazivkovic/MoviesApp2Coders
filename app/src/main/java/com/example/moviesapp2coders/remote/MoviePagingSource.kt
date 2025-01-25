package com.example.moviesapp2coders.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesapp2coders.domain.Movie

//PagingSource for tracking the next page that needs to be loaded when items are scrolled
internal class MoviePagingSource(
    private val repository: Repository
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: FIST_PAGE
            val (page, data) = repository.discoverMovies(nextPage)
            LoadResult.Page(
                data = data,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int {
        return FIST_PAGE
    }

    companion object {
        const val FIST_PAGE = 1
        const val PAGE_SIZE = 20
    }
}
