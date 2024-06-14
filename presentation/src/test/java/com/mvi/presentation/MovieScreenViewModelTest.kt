package com.mvi.presentation


import androidx.test.filters.SmallTest
import com.mvi.common.Resource
import com.mvi.domain.usecase.GetMovieListUseCase
import com.mvi.presentation.contract.MovieListScreenContract
import com.mvi.presentation.viewmodel.movie.MovieScreenViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
@SmallTest
class MovieScreenViewModelTest {
    private val dispatcher: CoroutineDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var movieListUseCase: GetMovieListUseCase
    private lateinit var movieScreenViewModel: MovieScreenViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this) // turn relaxUnitFun on for all mocks
        // Create MainViewModel before every test
        movieScreenViewModel = MovieScreenViewModel(
            movieListUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cancel()
        unmockkAll()
    }

    @Test
    fun `Given successful use case response When fetching movie list Then show success state with data`() =
        runTest {
            val movieData = TestDataGenerator.generateMovieListData()
            val success = Resource.Success(movieData)

            coEvery { movieListUseCase.invoke() } returns success

            expectThat(movieScreenViewModel.viewState.value).isA<MovieListScreenContract.State.Loading>()

            movieScreenViewModel.handleEvent(MovieListScreenContract.Event.OnListFetch)
            delay(100)

            expectThat(movieScreenViewModel.viewState.value).isA<MovieListScreenContract.State.Success>()
                .get { data }.isEqualTo(movieData)
        }

    @Test
    fun `Given error from use case When fetching movie list Then show error state with message`() =
        runTest {
            val data = Resource.Error(Exception(NO_DATA_FOUND))

            coEvery { movieListUseCase.invoke() } returns data

            expectThat(movieScreenViewModel.viewState.value).isA<MovieListScreenContract.State.Loading>()

            movieScreenViewModel.handleEvent(MovieListScreenContract.Event.OnListFetch)
            delay(100)

            expectThat(movieScreenViewModel.viewState.value).isA<MovieListScreenContract.State.Error>()
                .get { errorMessage }
                .isEqualTo(data.exception.message.toString())
        }

    private companion object {
        private const val NO_DATA_FOUND = "No data found"
    }
}
