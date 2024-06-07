package com.mvi.domain

import androidx.test.filters.SmallTest
import com.mvi.base.Resource
import com.mvi.domain.repository.movie.MovieRepository
import com.mvi.domain.usecase.GetMovieListUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
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
class GetMovieListUseCaseTest {

    private val dispatcher: CoroutineDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var movieRepository: MovieRepository

    private lateinit var getMovieListUseCase: GetMovieListUseCase


    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)
        getMovieListUseCase = GetMovieListUseCase(movieRepository, dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cancel()
    }


    @Test
    fun test_get_movie_use_case_success() = runTest {

        val model = TestDataGenerator.generateMovieEntityModel()

        val data = Resource.Success(model)

        //Given
        coEvery { movieRepository.fetchMovieList() } returns data
        //When
        val result = getMovieListUseCase.invoke()

        //Assertion
        Assert.assertEquals(result, data)
        // Then
        coVerify { movieRepository.fetchMovieList() }

    }


    @Test
    fun test_movie_use_case_failure() = runTest {
        val data = Resource.Error(NullPointerException(NO_DATA_FOUND))

        //Given
        coEvery { movieRepository.fetchMovieList() } returns data

        //When
        val result = getMovieListUseCase.invoke()


        //Assertion
        Assert.assertEquals(result, data)

        //Then
        coVerify { movieRepository.fetchMovieList() }
    }

    private companion object {
        private const val NO_DATA_FOUND = "No data found"
    }

}