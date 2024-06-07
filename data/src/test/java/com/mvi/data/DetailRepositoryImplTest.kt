package com.mvi.data

import androidx.test.filters.SmallTest
import com.google.gson.JsonParseException
import com.mvi.base.Resource
import com.mvi.data.mapper.detail.DetailDataDomainMapper
import com.mvi.data.repository.detail.DetailRepositoryImpl
import com.mvi.data.source.detail.DetailRemoteDataSource
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
import java.io.IOException
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
@SmallTest
class DetailRepositoryImplTest {
    private val dispatcher: CoroutineDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var detailRemoteDataSource: DetailRemoteDataSource

    private val detailDataDomainMapper = DetailDataDomainMapper()
    private lateinit var detailRepository: DetailRepository


    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)

        detailRepository = DetailRepositoryImpl(
            detailRemoteDataSource, detailDataDomainMapper
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
        coEvery { detailRemoteDataSource.fetchMovieDetail(ID) } returns model

        //When
        val result = detailRepository.fetchMovieDetail(ID)
        //Assertion

        Assert.assertEquals(
            result, Resource.Success(detailDataDomainMapper.from(model))
        )
        Assert.assertEquals(
            (result as Resource.Success).data,
            detailDataDomainMapper.from(model)
        )

        //Then
        coVerify { detailRemoteDataSource.fetchMovieDetail(ID) }

    }

    @Test
    fun test_movie_repository_impl_failure_no_data() = runTest {
        //Given
        coEvery { detailRemoteDataSource.fetchMovieDetail(ID) }

        //When && Assertions
        val result = detailRepository.fetchMovieDetail(ID)
        Assert.assertEquals(result.javaClass, Resource.Error::class.java)

        // Then
        coVerify { detailRemoteDataSource.fetchMovieDetail(ID) }

    }

    @Test
    fun test_movie_repository_impl_failure() = runTest {

        // Given
        coEvery { detailRemoteDataSource.fetchMovieDetail(ID) } throws Exception()


        // When && Assertions
        val result = detailRepository.fetchMovieDetail(ID)
        Assert.assertEquals(result.javaClass, Resource.Error::class.java)


        // Then
        coVerify { detailRemoteDataSource.fetchMovieDetail(ID) }

    }

    @Test
    fun test_movie_repository_impl_failure_parse() = runTest {

        // Given
        coEvery { detailRemoteDataSource.fetchMovieDetail(ID) } throws JsonParseException(
            PARSE_ERROR
        )


        // When && Assertions
        val result = detailRepository.fetchMovieDetail(ID)
        Assert.assertEquals(result.javaClass, Resource.Error::class.java)

        // Then
        coVerify { detailRemoteDataSource.fetchMovieDetail(ID) }

    }

    @Test
    fun test_movie_repository_impl_failure_io() = runTest {

        // Given
        coEvery { detailRemoteDataSource.fetchMovieDetail(ID) } throws IOException(
            NETWORK_ERROR
        )


        // When && Assertions
        val result = detailRepository.fetchMovieDetail(ID)
        Assert.assertEquals(result.javaClass, Resource.Error::class.java)

        // Then
        coVerify { detailRemoteDataSource.fetchMovieDetail(ID) }

    }

    private companion object {
        private const val ID = "id"
        private const val PARSE_ERROR = "Data parsing error"
        private const val NETWORK_ERROR = "Network error"
    }

}