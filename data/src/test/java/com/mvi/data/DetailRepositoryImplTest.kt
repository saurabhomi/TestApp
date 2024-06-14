package com.mvi.data

import com.google.gson.JsonParseException
import com.mvi.common.Resource
import com.mvi.data.api.ApiService
import com.mvi.data.mapper.detail.DetailDataDomainMapper
import com.mvi.data.repository.detail.DetailRepositoryImpl
import com.mvi.domain.model.detail.DetailDomainModel
import com.mvi.domain.repository.detail.DetailRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
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
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import java.io.IOException
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class) // Using MockitoJUnitRunner for stricter API
class DetailRepositoryImplTest {

    @MockK
    private lateinit var apiService: ApiService

    private val detailDataDomainMapper = DetailDataDomainMapper()
    private lateinit var detailRepository: DetailRepository

    private val dispatcher = StandardTestDispatcher()

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
        unmockkAll()
    }

    @Test
    fun `Given successful API response When fetching movie detail Then return success`() = runTest {
        val model = TestDataGenerator.generateMovieDetailData()

        coEvery { apiService.getMovieDetails(ID) } returns Response.success(model)

        val result = detailRepository.fetchMovieDetail(ID)

        expectThat(result)
            .isA<Resource.Success<DetailDomainModel>>()
            .get { data }
            .isEqualTo(detailDataDomainMapper.from(model))
    }

    @Test
    fun `Given no data from API When fetching movie detail Then return error`() = runTest {

        coEvery { apiService.getMovieDetails(ID) }

        val result = detailRepository.fetchMovieDetail(ID)
        expectThat(result).isA<Resource.Error>()
    }

    @Test
    fun `Given exception thrown by API When fetching movie detail Then return error`() = runTest {

        coEvery { apiService.getMovieDetails(ID) } throws Exception()

        val result = detailRepository.fetchMovieDetail(ID)
        expectThat(result).isA<Resource.Error>()
    }

    @Test
    fun `Given JsonParseException thrown by API When fetching movie detail Then return error`() = runTest {

        coEvery { apiService.getMovieDetails(ID) } throws JsonParseException(
            PARSE_ERROR
        )

        val result = detailRepository.fetchMovieDetail(ID)
        expectThat(result).isA<Resource.Error>()
    }

    @Test
    fun `Given IOException thrown by API When fetching movie detail Then return error`() = runTest {

        coEvery { apiService.getMovieDetails(ID) } throws IOException(
            NETWORK_ERROR
        )

        val result = detailRepository.fetchMovieDetail(ID)
        expectThat(result).isA<Resource.Error>()
    }

    private companion object {
        private const val ID = "id"
        private const val PARSE_ERROR = "Data parsing error"
        private const val NETWORK_ERROR = "Network error"
    }
}
