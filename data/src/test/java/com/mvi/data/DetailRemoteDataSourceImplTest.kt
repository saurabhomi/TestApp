package com.mvi.data

import androidx.test.filters.SmallTest
import com.mvi.data.source.detail.DetailRemoteDataSource
import com.mvi.data.source.detail.DetailRemoteDataSourceImpl
import com.mvi.network.api.ApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
@SmallTest
class DetailRemoteDataSourceImplTest {
    @MockK
    private lateinit var apiService: ApiService
    private val detailNetworkDataMapper =
        com.mvi.data.mapper.detail.DetailNetworkDataMapper()

    private lateinit var remoteDataSource: DetailRemoteDataSource

    @Before
    fun setup() {
        MockKAnnotations.init(
            this
        ) // turn relaxUnitFun on for all mocks
        // Create RemoteDataSourceImp before every test
        remoteDataSource = DetailRemoteDataSourceImpl(
            apiService = apiService,
            movieMapper = detailNetworkDataMapper,
        )
    }

    @Test
    fun test_get_detail_success() = runTest {

        val model = TestDataGenerator.generateMovieDetailNetworkData()


        // Given
        coEvery {
            apiService.getMovieDetails(any()).body()
        } returns model

        // When
        val result = remoteDataSource.fetchMovieDetail("")

        // Then
        coVerify { apiService.getMovieDetails(any()) }

        // Assertion
        val expected = detailNetworkDataMapper.from(model)
        Assert.assertEquals(result, expected)
    }

    @Test(expected = Exception::class)
    fun test_get_movie_list_fail() = runTest {

        // Given
        coEvery { apiService.getMovieDetails("") } throws Exception()

        // When
        remoteDataSource.fetchMovieDetail("")

        // Then
        coVerify { apiService.getMovieDetails("") }

    }
}