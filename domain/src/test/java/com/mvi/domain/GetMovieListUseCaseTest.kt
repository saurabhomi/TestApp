package com.mvi.domain

import androidx.test.filters.SmallTest
import com.mvi.common.Resource
import com.mvi.domain.model.movie.MovieDomainModel
import com.mvi.domain.repository.movie.MovieRepository
import com.mvi.domain.usecase.GetMovieListUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
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
class GetMovieListUseCaseTest {

    private val dispatcher: CoroutineDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var movieRepository: MovieRepository

    private lateinit var getMovieListUseCase: GetMovieListUseCase


    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)
        getMovieListUseCase = GetMovieListUseCase(movieRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cancel()
        unmockkAll()
    }

    @Test
    fun `Given successful repository response When invoking use case Then return success`() = runTest {
        val model = TestDataGenerator.generateMovieEntityModel()

        val success = Resource.Success(model)

        coEvery { movieRepository.fetchMovieList() } returns success

        val result = getMovieListUseCase.invoke()


        expectThat(result).isA<Resource.Success<MovieDomainModel>>()
            .get { data }.isEqualTo(model)

    }

    @Test
    fun `Given error from repository When invoking use case Then return error`() = runTest {
        val data = Resource.Error(NullPointerException(NO_DATA_FOUND))

        coEvery { movieRepository.fetchMovieList() } returns data

        val result = getMovieListUseCase.invoke()

        expectThat(result).isA<Resource.Error>()

    }

    private companion object {
        private const val NO_DATA_FOUND = "No data found"
    }
}
