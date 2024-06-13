package com.mvi.domain

import androidx.test.filters.SmallTest
import com.mvi.common.Resource
import com.mvi.domain.repository.detail.DetailRepository
import com.mvi.domain.usecase.GetMovieDetailUseCase
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
class GetMovieDetailUseCaseTest {

    private val dispatcher: CoroutineDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var detailRepository: DetailRepository

    private lateinit var detailUseCase: GetMovieDetailUseCase


    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)
        detailUseCase = GetMovieDetailUseCase(
            detailRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cancel()
    }

    @Test
    fun test_detail_use_case_success() = runTest {
        val model = TestDataGenerator.generateMovieDetailEntityModel()

        val data = Resource.Success(model)

        //Given
        coEvery { detailRepository.fetchMovieDetail(any()) } returns data

        //When
        val result = detailUseCase.invoke("")
        Assert.assertEquals(result, data)
        //Then
        coVerify { detailRepository.fetchMovieDetail(any()) }


    }


    @Test
    fun test_detail_use_case_failure() = runTest {

        val data = Resource.Error(NullPointerException(NO_DATA_FOUND))

        //Given
        coEvery { detailRepository.fetchMovieDetail(any()) } returns data
        //When
        val result = detailUseCase.invoke("")
        Assert.assertEquals(result, data)
        //Then
        coVerify { detailRepository.fetchMovieDetail(any()) }
    }

    private companion object {
        private const val NO_DATA_FOUND = "No data found"
    }
}