package com.mvi.data


import androidx.test.filters.SmallTest
import com.mvi.data.source.movie.MovieRemoteDataSource
import com.mvi.data.source.movie.MovieRemoteDataSourceImpl
import com.mvi.network.api.ApiService
import com.mvi.network.model.movie.MovieListNetworkModel
import com.mvi.network.model.movie.MovieNetworkModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@SmallTest
class MovieRemoteDataSourceImpTest {

    @MockK
    private lateinit var apiService: ApiService
    private val movieNetworkDataMapper =
        com.mvi.data.mapper.movie.MovieNetworkDataMapper()

    private lateinit var remoteDataSource: MovieRemoteDataSource

    @Before
    fun setup() {
        MockKAnnotations.init(
            this
        ) // turn relaxUnitFun on for all mocks
        // Create RemoteDataSourceImp before every test
        remoteDataSource = MovieRemoteDataSourceImpl(
            apiService = apiService,
            movieMapper = movieNetworkDataMapper,
        )
    }

    @Test
    fun test_get_movie_list_success() = runTest {

        val movieListResultsDataModel: MovieNetworkModel = mockk()

        every { movieListResultsDataModel.result } returns getList()


        // Given
        coEvery {
            apiService.getMovieList().body()
        } returns movieListResultsDataModel

        // When
        val result = remoteDataSource.fetchMovieList()

        // Then
        coVerify { apiService.getMovieList() }

        // Assertion
        val expected = movieNetworkDataMapper.from(movieListResultsDataModel)
        Assert.assertEquals(result, expected)
    }

    private fun getList(): List<MovieListNetworkModel> {
        return TestDataGenerator.generateMovieData().result!!
    }

    @Test(expected = Exception::class)
    fun test_get_movie_list_fail() = runTest {

        // Given
        coEvery { apiService.getMovieList() } throws Exception()

        // When
        remoteDataSource.fetchMovieList()

        // Then
        coVerify { apiService.getMovieList() }

    }
}