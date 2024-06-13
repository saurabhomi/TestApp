package com.mvi.data

import androidx.test.filters.SmallTest
import com.google.gson.JsonParseException
import com.mvi.common.Resource
import com.mvi.data.api.ApiService
import com.mvi.data.mapper.detail.DetailDataDomainMapper
import com.mvi.data.repository.detail.DetailRepositoryImpl
import com.mvi.domain.repository.detail.DetailRepository
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
class DetailRepositoryImplTest {
    private val dispatcher: CoroutineDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var apiService: ApiService

    private val detailDataDomainMapper = DetailDataDomainMapper()
    private lateinit var detailRepository: DetailRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)

        detailRepository = DetailRepositoryImpl(
            apiService, detailDataDomainMapper, dispatcher
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cancel()
    }

    @Test
    fun test_detail_repository_success() = runTest {

        val model = TestDataGenerator.generateMovieDetailData()

        //Given
        coEvery { apiService.getMovieDetails(ID) } returns Response.success(model)

        //When
        val result = detailRepository.fetchMovieDetail(ID)
        //Assertion
        Assert.assertEquals(
            (result as Resource.Success).data, detailDataDomainMapper.from(model)
        )

        //Then
        coVerify { apiService.getMovieDetails(ID) }

    }

    @Test
    fun test_movie_repository_impl_failure_no_data() = runTest {
        //Given
        coEvery { apiService.getMovieDetails(ID) }

        //When && Assertions
        val result = detailRepository.fetchMovieDetail(ID)
        Assert.assertEquals(result.javaClass, Resource.Error::class.java)

        // Then
        coVerify { apiService.getMovieDetails(ID) }

    }

    @Test
    fun test_movie_repository_impl_failure() = runTest {

        // Given
        coEvery { apiService.getMovieDetails(ID) } throws Exception()

        // When && Assertions
        val result = detailRepository.fetchMovieDetail(ID)
        Assert.assertEquals(result.javaClass, Resource.Error::class.java)

        // Then
        coVerify { apiService.getMovieDetails(ID) }

    }

    @Test
    fun test_movie_repository_impl_failure_parse() = runTest {

        // Given
        coEvery { apiService.getMovieDetails(ID) } throws JsonParseException(
            PARSE_ERROR
        )

        // When && Assertions
        val result = detailRepository.fetchMovieDetail(ID)
        Assert.assertEquals(result.javaClass, Resource.Error::class.java)

        // Then
        coVerify { apiService.getMovieDetails(ID) }

    }

    @Test
    fun test_movie_repository_impl_failure_io() = runTest {

        // Given
        coEvery { apiService.getMovieDetails(ID) } throws IOException(
            NETWORK_ERROR
        )

        // When && Assertions
        val result = detailRepository.fetchMovieDetail(ID)
        Assert.assertEquals(result.javaClass, Resource.Error::class.java)

        // Then
        coVerify { apiService.getMovieDetails(ID) }

    }

    private companion object {
        private const val ID = "id"
        private const val PARSE_ERROR = "Data parsing error"
        private const val NETWORK_ERROR = "Network error"
    }

}