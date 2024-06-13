package com.mvi.data

import androidx.test.filters.SmallTest
import com.google.gson.JsonParseException
import com.mvi.common.Resource
import com.mvi.data.api.ApiService
import com.mvi.data.mapper.movie.MovieListDataDomainMapper
import com.mvi.data.repository.movie.MovieRepositoryImpl
import com.mvi.domain.repository.movie.MovieRepository
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
import retrofit2.Response
import java.io.IOException
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
@SmallTest
class MovieRepositoryImplTest {

    private val dispatcher: CoroutineDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var apiService: ApiService

    private val movieListDataDomainMapper = MovieListDataDomainMapper()

    private lateinit var repository: MovieRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)

        repository = MovieRepositoryImpl(
            apiService, movieListDataDomainMapper, dispatcher
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cancel()
    }

    @Test
    fun test_movie_repository_impl_success() = runTest {

        val model = TestDataGenerator.generateMovieListData()

        coEvery { apiService.getMovieList() } returns Response.success(model)

        val result = repository.fetchMovieList()

        Assert.assertEquals(
            (result as Resource.Success).data,
            movieListDataDomainMapper.from(model)
        )

        // Then
        coVerify { apiService.getMovieList() }

    }


    @Test
    fun test_movie_repository_impl_failure() = runTest {

        // Given
        coEvery { apiService.getMovieList() } throws Exception()

        // When && Assertions
        val result = repository.fetchMovieList()
        Assert.assertEquals(result.javaClass, Resource.Error::class.java)

        // Then
        coVerify { apiService.getMovieList() }

    }

    @Test
    fun test_movie_repository_impl_failure_parse() = runTest {

        // Given
        coEvery { apiService.getMovieList() } throws JsonParseException(
            PARSE_ERROR
        )

        // When && Assertions
        val result = repository.fetchMovieList()
        Assert.assertEquals(result.javaClass, Resource.Error::class.java)

        // Then
        coVerify { apiService.getMovieList() }

    }

    @Test
    fun test_movie_repository_impl_failure_io() = runTest {

        // Given
        coEvery { apiService.getMovieList() } throws IOException(
            NETWORK_ERROR
        )
        // When && Assertions
        val result = repository.fetchMovieList()
        Assert.assertEquals(result.javaClass, Resource.Error::class.java)

        // Then
        coVerify { apiService.getMovieList() }

    }

    @Test
    fun test_movie_repository_impl_failure_null_pointer() = runTest {

        // Given
        coEvery { apiService.getMovieList() } throws NullPointerException()


        // When && Assertions
        val result = repository.fetchMovieList()
        Assert.assertEquals(result.javaClass, Resource.Error::class.java)

        // Then
        coVerify { apiService.getMovieList() }

    }

    private companion object {
        private const val PARSE_ERROR = "Data parsing error"
        private const val NETWORK_ERROR = "Network error"
    }
}