package com.example.moviesapp2coders.presentation

import androidx.paging.Pager
import androidx.paging.PagingData
import app.cash.turbine.test
import com.example.moviesapp2coders.AppBaseTest
import com.example.moviesapp2coders.domain.Movie
import com.example.moviesapp2coders.remote.InternetAvailability
import com.example.moviesapp2coders.remote.Repository
import com.example.moviesapp2coders.remote.Result
import com.example.moviesapp2coders.remote.Status
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class MoviesViewModelTest : AppBaseTest() {

    private val favoriteMoviesFlow = MutableSharedFlow<List<Movie>>(replay = 1)
    private val mockRepository = mockk<Repository>(relaxed = true) {
        every { favoriteMovies } returns favoriteMoviesFlow
    }
    private val internetFlow = MutableSharedFlow<Status>(replay = 1)
    private val mockInternetAvailability = mockk<InternetAvailability>(relaxed = true) {
        every { internetConnection } returns internetFlow
    }

    private val pagerFlow = MutableSharedFlow<PagingData<Movie>>(replay = 1)
    private val mockPager = mockk<Pager<Int, Movie>>(relaxed = true) {
        every { flow } returns pagerFlow
    }

    private lateinit var moviesViewModel: MoviesViewModel

    @BeforeEach
    internal fun setUp() {
        moviesViewModel = MoviesViewModel(
            repository = mockRepository,
            pager = mockPager,
            internetAvailability = mockInternetAvailability
        )
    }

    @MethodSource("createInternetStatusScenarios")
    @ParameterizedTest
    internal fun `test internet availability stream`(status: Status) = runTest {
        internetFlow.emit(status)
        moviesViewModel.hasInternet.test {
            assertEquals(actual = awaitItem(), expected = status)
        }
    }

    @MethodSource("favoriteMoviesScenarios")
    @ParameterizedTest
    internal fun `test favorite movies flow emission`(moviesList: List<Movie>) = runTest {
        favoriteMoviesFlow.emit(moviesList)
        moviesViewModel.favoriteMovies.test {
            assertEquals(actual = awaitItem().size, expected = moviesList.size)
        }
    }

    //TODO: comeback to this
    @MethodSource("pagerMoviesCases")
    @ParameterizedTest
    internal fun `test pager movies flow emission`(movie: Movie) = runTest {
        val pagingData = PagingData.from(listOf(movie))
        pagerFlow.emit(pagingData)
        moviesViewModel.moviesPagerFlow.test {
            assertTrue(awaitItem() is PagingData)
        }
    }

    @MethodSource("provideSearchMoviesScenarios")
    @ParameterizedTest
    internal fun `test search movie`(searchResult: Result<List<Movie>>) = runTest {
        coEvery { mockRepository.searchMovie(any(), any()) } returns flowOf(searchResult)
        moviesViewModel.onQueryChanged("123")
        moviesViewModel.searchResults.test {
            val result = awaitItem()
            assertTrue(result is Result.Success)
            when (awaitItem()) {
                is Result.Loading ->{
                    assertTrue(true)
                }
                is Result.Success ->{
                    assertTrue(true)
                }
                is Result.Error ->{
                    assertTrue(true)
                }
            }
            cancelAndConsumeRemainingEvents()
        }
    }

    companion object {
        @JvmStatic
        fun createInternetStatusScenarios(): Stream<Array<Status>> = Stream.of(
            arrayOf(Status.Available),
            arrayOf(Status.Unavailable),
            arrayOf(Status.Lost),
            arrayOf(Status.Losing),
        )

        private val baseMovie = Movie(
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
            isFavorite = false,
        )

        @JvmStatic
        fun favoriteMoviesScenarios(): Stream<Array<List<Movie>>> = Stream.of(
            arrayOf(listOf(baseMovie)),
            arrayOf(listOf(baseMovie, baseMovie.copy(id = 2))),
            arrayOf(listOf(baseMovie, baseMovie.copy(id = 2), baseMovie.copy(id = 3))),
            arrayOf(
                listOf(
                    baseMovie,
                    baseMovie.copy(id = 2),
                    baseMovie.copy(id = 3),
                    baseMovie.copy(id = 4)
                )
            ),
        )

        @JvmStatic
        fun pagerMoviesCases(): Stream<Array<Movie>> = Stream.of(
            arrayOf(baseMovie),
            arrayOf(baseMovie.copy(id = 2)),
            arrayOf(baseMovie.copy(id = 3)),
            arrayOf(baseMovie.copy(id = 4)),
        )

        @JvmStatic
        fun provideSearchMoviesScenarios(): Stream<Array<Result<*>>> = Stream.of(
            arrayOf(Result.Loading),
            arrayOf(Result.Success(baseMovie)),
            arrayOf(Result.Error(Exception())),
        )
    }
}
