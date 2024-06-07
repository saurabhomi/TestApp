package com.mvi.data

import androidx.test.filters.SmallTest
import com.google.gson.JsonParseException
import com.mvi.base.Resource
import com.mvi.data.mapper.movie.MovieListDataDomainMapper
import com.mvi.data.repository.movie.MovieRepositoryImpl
import com.mvi.data.source.movie.MovieRemoteDataSource
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
import java.io.IOException
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
@SmallTest
class MovieRepositoryImplTest {

    private val dispatcher: CoroutineDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var movieRemoteDataSource: MovieRemoteDataSource

    private val movieListDataDomainMapper = MovieListDataDomainMapper()

    private lateinit var repository: MovieRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)

        repository = MovieRepositoryImpl(
            movieRemoteDataSource, movieListDataDomainMapper
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

        coEvery { movieRemoteDataSource.fetchMovieList() } returns model

        val result = repository.fetchMovieList()
        Assert.assertEquals(
            result, Resource.Success(movieListDataDomainMapper.from(model))
        )
        Assert.assertEquals(
            (result as Resource.Success).data,
            movieListDataDomainMapper.from(model)
        )

        // Then
        coVerify { movieRemoteDataSource.fetchMovieList() }

    }


    @Test
    fun test_movie_repository_impl_failure() = runTest {

        // Given
        coEvery { movieRemoteDataSource.fetchMovieList() } throws Exception()


        // When && Assertions
        val result = repository.fetchMovieList()
        Assert.assertEquals(result.javaClass, Resource.Error::class.java)

        // Then
        coVerify { movieRemoteDataSource.fetchMovieList() }

    }

    @Test
    fun test_movie_repository_impl_failure_parse() = runTest {

        // Given
        coEvery { movieRemoteDataSource.fetchMovieList() } throws JsonParseException(
            PARSE_ERROR
        )


        // When && Assertions
        val result = repository.fetchMovieList()
        Assert.assertEquals(result.javaClass, Resource.Error::class.java)

        // Then
        coVerify { movieRemoteDataSource.fetchMovieList() }

    }

    @Test
    fun test_movie_repository_impl_failure_io() = runTest {

        // Given
        coEvery { movieRemoteDataSource.fetchMovieList() } throws IOException(
            NETWORK_ERROR
        )


        // When && Assertions
        val result = repository.fetchMovieList()
        Assert.assertEquals(result.javaClass, Resource.Error::class.java)

        // Then
        coVerify { movieRemoteDataSource.fetchMovieList() }

    }

    @Test
    fun test_movie_repository_impl_failure_null_pointer() = runTest {

        // Given
        coEvery { movieRemoteDataSource.fetchMovieList() } throws NullPointerException()


        // When && Assertions
        val result = repository.fetchMovieList()
        Assert.assertEquals(result.javaClass, Resource.Error::class.java)

        // Then
        coVerify { movieRemoteDataSource.fetchMovieList() }

    }

    private companion object {
        private const val PARSE_ERROR = "Data parsing error"
        private const val NETWORK_ERROR = "Network error"
    }
}