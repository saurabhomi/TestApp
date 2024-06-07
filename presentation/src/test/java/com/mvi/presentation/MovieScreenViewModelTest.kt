package com.mvi.presentation


import androidx.test.filters.SmallTest
import com.mvi.base.Resource
import com.mvi.common.constants.Constants.Companion.NO_DATA_FOUND
import com.mvi.domain.usecase.GetMovieListUseCase
import com.mvi.presentation.contract.MovieListScreenContract
import com.mvi.presentation.mapper.movie.MovieListDomainUiMapper
import com.mvi.presentation.viewmodel.movie.MovieScreenViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
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
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
@SmallTest
class MovieScreenViewModelTest {

    private val dispatcher: CoroutineDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var movieListUseCase: GetMovieListUseCase

    private val movieListDomainUiMapper = MovieListDomainUiMapper()

    private lateinit var movieScreenViewModel: MovieScreenViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this) // turn relaxUnitFun on for all mocks
        // Create MainViewModel before every test
        movieScreenViewModel = MovieScreenViewModel(
            movieListUseCase, movieListDomainUiMapper
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cancel()
    }

    @Test
    fun test_fetch_movie_list_success() = runTest {

        val data = TestDataGenerator.generateMovieListData()
        val model = Resource.Success(data)

        // Given
        coEvery { movieListUseCase.invoke() } returns model
        assert(movieScreenViewModel.viewState.value == MovieListScreenContract.State.Idle)

        // When && Assertions
        movieScreenViewModel.setEvent(MovieListScreenContract.Event.OnListFetch)
        delay(100)
        assert(
            movieScreenViewModel.viewState.value == MovieListScreenContract.State.Success(
                movieListDomainUiMapper.from(
                    data
                )
            )
        )
        movieScreenViewModel.setEvent(MovieListScreenContract.Event.OnListLoaded)

        assert(
            (movieScreenViewModel.viewState.value as MovieListScreenContract.State.Success).data == movieListDomainUiMapper.from(
                data
            )
        )
        // Then
        coVerify { movieListUseCase.invoke() }
    }

    @Test
    fun test_fetch_movie_list_fail() = runTest {

        val data = Resource.Error(Exception(NO_DATA_FOUND))
        // Given
        coEvery { movieListUseCase.invoke() } returns data
        assert(movieScreenViewModel.viewState.value == MovieListScreenContract.State.Idle)


        // When && Assertions
        movieScreenViewModel.setEvent(MovieListScreenContract.Event.OnListFetch)
        delay(100)
        assert(
            movieScreenViewModel.viewState.value == MovieListScreenContract.State.Error(
                data.exception.message.toString()
            )
        )

        movieScreenViewModel.setEvent(
            MovieListScreenContract.Event.OnError(
                data.exception.message.toString()
            )
        )

        assert(
            (movieScreenViewModel.viewState.value as MovieListScreenContract.State.Error).errorMessage == data.exception.message.toString()
        )


        // Then
        coVerify { movieListUseCase.invoke() }
    }

    @Test
    fun test_select_movie_item() = runTest {

        val movieItem = TestDataGenerator.generateMovieListData()

        val data = Resource.Success(movieItem)

        // Given
        coEvery { movieListUseCase.invoke() } returns data

        Assert.assertEquals(
            movieScreenViewModel.viewState.value,
            MovieListScreenContract.State.Idle
        )
        // When && Assertions
        movieScreenViewModel.setEvent(MovieListScreenContract.Event.OnListFetch)
        delay(100)

        Assert.assertEquals(
            movieScreenViewModel.viewState.value,
            MovieListScreenContract.State.Success(
                movieListDomainUiMapper.from(movieItem)
            )
        )
        movieScreenViewModel.setEvent(MovieListScreenContract.Event.OnListLoaded)
        Assert.assertEquals(
            (movieScreenViewModel.viewState.value as MovieListScreenContract.State.Success).data.result,
            movieListDomainUiMapper.from(movieItem).result
        )
        movieScreenViewModel.setEvent(
            MovieListScreenContract.Event.OnListItemClick(
                movieListDomainUiMapper.from(movieItem).result?.get(0)!!
            )
        )

//     Then
        coVerify { movieListUseCase.invoke() }
    }
}