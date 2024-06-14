package com.mvi.data

import androidx.test.filters.SmallTest
import com.google.gson.JsonParseException
import com.mvi.common.Resource
import com.mvi.data.api.ApiService
import com.mvi.data.mapper.movie.MovieListDataDomainMapper
import com.mvi.data.repository.movie.MovieRepositoryImpl
import com.mvi.domain.model.movie.MovieDomainModel
import com.mvi.domain.repository.movie.MovieRepository
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
import retrofit2.Response
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
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
        unmockkAll()
    }

    @Test
    fun `Given successful API response When fetching movie list Then return success`() =
        runTest {
            val model = TestDataGenerator.generateMovieListData()

            coEvery { apiService.getMovieList() } returns Response.success(model)

            val result = repository.fetchMovieList()

            expectThat(result).isA<Resource.Success<MovieDomainModel>>()
                .get { data }.isEqualTo(movieListDataDomainMapper.from(model))

        }

    @Test
    fun `Given exception thrown by API When fetching movie list Then return error`() =
        runTest {

            coEvery { apiService.getMovieList() } throws Exception()

            val result = repository.fetchMovieList()
            expectThat(result).isA<Resource.Error>()
        }

    @Test
    fun `Given JsonParseException thrown by API When fetching movie list Then return error`() =
        runTest {

            coEvery { apiService.getMovieList() } throws JsonParseException(
                PARSE_ERROR
            )

            val result = repository.fetchMovieList()
            expectThat(result).isA<Resource.Error>()
        }

    @Test
    fun `Given IOException thrown by API When fetching movie list Then return error`() =
        runTest {

            coEvery { apiService.getMovieList() } throws IOException(
                NETWORK_ERROR
            )

            val result = repository.fetchMovieList()
            expectThat(result).isA<Resource.Error>()
        }

    @Test
    fun `Given NullPointerException thrown by API When fetching movie list Then return error`() =
        runTest {

            coEvery { apiService.getMovieList() } throws NullPointerException()

            val result = repository.fetchMovieList()
            expectThat(result).isA<Resource.Error>()
        }

    private companion object {
        private const val PARSE_ERROR = "Data parsing error"
        private const val NETWORK_ERROR = "Network error"
    }
}
