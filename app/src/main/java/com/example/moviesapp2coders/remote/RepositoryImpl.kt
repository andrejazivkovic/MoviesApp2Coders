package com.example.moviesapp2coders.remote

import androidx.room.withTransaction
import com.example.moviesapp2coders.domain.Movie
import com.example.moviesapp2coders.domain.MovieToggle
import com.example.moviesapp2coders.local.MoviesDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class RepositoryImpl @Inject constructor(
    private val internetAvailability: InternetAvailability,
    private val moviesApi: MoviesApi,
    private val moviesDatabase: MoviesDatabase
) : Repository {
    // Check if internet is available
    // Fetch data from the API and store it in the local database
    // If no internet, get movies from the local database
    override suspend fun discoverMovies(page: Int): Pair<Int, List<Movie>> {
        return if (internetAvailability.hasInternet()) {
            val discoverMoviesCall = moviesApi.discoverMovies(page)
            val movieEntities = discoverMoviesCall.results.map { it.toMovieEntity() }
            moviesDatabase.withTransaction {
                moviesDatabase.movieDao.clearMoviesTable()
                moviesDatabase.movieDao.upsertMoviesCollection(movieEntities)
            }
            discoverMoviesCall.page to movieEntities.map { it.toMovie() }
        } else {
            page to moviesDatabase.movieDao.gelAllAvailableMovies().map { it.toMovie() }
        }
    }

    //Add movies to favorites
    override suspend fun updateMovieFavorite(movie: Movie, isFavorite: Boolean) =
        moviesDatabase.movieDao.insertMovie(movie.toMovieEntity(isFavorite = isFavorite))

    //Get all favorite movies
    override val favoriteMovies: Flow<List<Movie>> =
        moviesDatabase.movieDao.getFavoriteMovies().map { entity ->
            entity.map {
                it.toMovie()
            }
        }
    // Cold flow for manually emitting when suspend api call is resumed also wrapped in Result so can
    //handle outcomes easier
    override suspend fun searchMovie(
        movieToggle: MovieToggle,
        query: String
    ): Flow<Result<List<Movie>>> =
        flow {
            emit(Result.Loading)
            if (movieToggle == MovieToggle.MOVIE) {
                try {
                    val moviesResult = moviesApi.searchMovie(query).results.map { it.toMovie() }
                    emit(Result.Success(moviesResult))
                } catch (exception: Exception) {
                    emit(Result.Error(exception))
                }
            } else {
                try {
                    val tvShowResult = moviesApi.searchTvShow(query).results.map { it.toMovie() }
                    emit(Result.Success(tvShowResult))
                } catch (exception: Exception) {
                    emit(Result.Error(exception))
                }
            }
        }.distinctUntilChanged()
}
