package com.example.moviesapp2coders.remote

import app.cash.turbine.test
import com.example.moviesapp2coders.AppBaseTest
import com.example.moviesapp2coders.domain.Movie
import com.example.moviesapp2coders.domain.MovieToggle
import com.example.moviesapp2coders.local.MovieEntity
import com.example.moviesapp2coders.local.MoviesDatabase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertTrue

internal class RepositoryImplTest : AppBaseTest() {
    private val mockInternetAvailability = mockk<InternetAvailability>(relaxed = true)
    private val mockMovieApi = mockk<MoviesApi>(relaxed = true)
    private val favoriteMoviesFlow = MutableSharedFlow<List<MovieEntity>>(replay = 1)
    private val mockMoviesDatabase = mockk<MoviesDatabase>(relaxed = true) {
        every { movieDao.getFavoriteMovies() } returns favoriteMoviesFlow
    }

    private lateinit var repository: Repository

    @BeforeEach
    internal fun setUp() {
        repository = RepositoryImpl(
            internetAvailability = mockInternetAvailability,
            moviesApi = mockMovieApi,
            moviesDatabase = mockMoviesDatabase
        )
    }

    @Test
    internal fun `test favorite movies flow`() = runTest {
        val movieEntity1 = MovieEntity(
            id = 1,
            originalLanguage = "en",
            originalTitle = "testOriginalTitle",
            overview = "testOverview",
            popularity = 1f,
            posterPath = "testPosterPath",
            releaseDate = "testReleaseDate",
            title = "testTitle",
            voteAverage = 1f,
            voteCount = 1,
            isFavorite = true
        )
        val movieEntity2 = movieEntity1.copy(id = 2)
        val moviesEntityList = listOf(movieEntity1, movieEntity2)
        favoriteMoviesFlow.emit(moviesEntityList)
        repository.favoriteMovies.test {
            val result = awaitItem()
            assertTrue(result.isNotEmpty() && result.all { it.isFavorite })
        }
    }

    @Test
    internal fun `test insert movie invocation`() = runTest {
        val baseMovie = MovieDTO(
            id = 1,
            original_language = "en",
            original_title = "testOriginalTitle",
            overview = "testOverview",
            popularity = 1f,
            poster_path = "testPosterPath",
            release_date = "testReleaseDate",
            title = "testTitle",
            vote_average = 1f,
            vote_count = 1,
        ).toMovie()
        repository.updateMovieFavorite(movie = baseMovie, isFavorite = true)
        coVerify {
            mockMoviesDatabase.movieDao.insertMovie(movie = baseMovie.toMovieEntity(true))
        }
    }

    @MethodSource("createSearchMovieScenarios")
    @ParameterizedTest
    internal fun `test search movie`(
        moviesResult: MoviesResult?,
        expected: Result<List<Movie>>,
        movieToggle: MovieToggle,
        query: String
    ) = runTest {
        if (moviesResult != null) {
            coEvery { mockMovieApi.searchMovie(any()) } returns moviesResult
        } else {
            coEvery { mockMovieApi.searchMovie(any()) } throws Exception()
        }

        repository.searchMovie(movieToggle = movieToggle, query = query).test {
            assertTrue(awaitItem() is Result.Loading)
            val emittedResult = awaitItem()
            if (expected is Result.Success) {
                assertTrue(expected.data.isNotEmpty())
            } else {
                assertTrue(emittedResult is Result.Error)
            }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    internal fun `test discover getting offline support`() = runTest {
        every { mockInternetAvailability.hasInternet() } returns false
        repository.discoverMovies(1)
        coVerify {
            mockMoviesDatabase.movieDao.gelAllAvailableMovies()
        }
    }

    @MethodSource("createSearchTvShowScenarios")
    @ParameterizedTest
    internal fun `test search tv show`(
        tvShowResult: TvShowResult?,
        expected: Result<List<Movie>>,
        movieToggle: MovieToggle,
        query: String
    ) = runTest {
        if (tvShowResult != null) {
            coEvery { mockMovieApi.searchTvShow(any()) } returns tvShowResult
        } else {
            coEvery { mockMovieApi.searchTvShow(any()) } throws Exception()
        }

        repository.searchMovie(movieToggle = movieToggle, query = query).test {
            assertTrue(awaitItem() is Result.Loading)
            val emittedResult = awaitItem()
            if (expected is Result.Success) {
                assertTrue(expected.data.isNotEmpty())
            } else {
                assertTrue(emittedResult is Result.Error)
            }
            cancelAndIgnoreRemainingEvents()
        }
    }

    companion object {
        private val baseMovieDTO = MovieDTO(
            id = 1,
            original_language = "en",
            original_title = "testOriginalTitle",
            overview = "testOverview",
            popularity = 1f,
            poster_path = "testPosterPath",
            release_date = "testReleaseDate",
            title = "testTitle",
            vote_average = 1f,
            vote_count = 1,
        )
        private val baseMovieResponse = MoviesResult(page = 0, results = listOf(baseMovieDTO))

        private val baseTvShowDTO = TvShowDTO(
            id = 1,
            original_language = "en",
            original_name = "testOriginalTitle",
            overview = "testOverview",
            popularity = 1f,
            poster_path = "testPosterPath",
            first_air_date = "testReleaseDate",
            name = "testTitle",
            vote_average = 1f,
            vote_count = 1,
        )
        private val baseTvShowResult = TvShowResult(page = 0, results = listOf(baseTvShowDTO))

        @JvmStatic
        fun createSearchMovieScenarios() = Stream.of(
            arrayOf(
                baseMovieResponse,
                Result.Success(baseMovieResponse.results),
                MovieToggle.MOVIE,
                "123"
            ),
            arrayOf(null, Result.Error(Exception()), MovieToggle.MOVIE, "123")
        )

        @JvmStatic
        fun createSearchTvShowScenarios() = Stream.of(
            arrayOf(
                baseTvShowResult,
                Result.Success(baseTvShowResult.results),
                MovieToggle.TV_SHOW,
                "123"
            ),
            arrayOf(null, Result.Error(Exception()), MovieToggle.TV_SHOW, "123")
        )
    }
}
