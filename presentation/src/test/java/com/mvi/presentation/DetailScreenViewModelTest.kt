package com.mvi.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.test.filters.SmallTest
import com.mvi.common.Resource
import com.mvi.domain.usecase.GetMovieDetailUseCase
import com.mvi.presentation.Constants.Companion.Args.MOVIE_ID
import com.mvi.presentation.contract.DetailScreenContract
import com.mvi.presentation.viewmodel.detail.DetailScreenViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
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
class DetailScreenViewModelTest {

    private val dispatcher: CoroutineDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var savedStateHandle: SavedStateHandle

    @MockK
    private lateinit var detailUseCase: GetMovieDetailUseCase


    private lateinit var detailScreenViewModel: DetailScreenViewModel


    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(
            this
        )
        every {
            savedStateHandle.get<String>(MOVIE_ID)
        } returns ID
        detailScreenViewModel = DetailScreenViewModel(
            detailUseCase, savedStateHandle
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cancel()
    }

    @Test
    fun test_fetch_movie_detail_success() = runTest {

        val model = TestDataGenerator.generateMovieDetailData()
        val data = Resource.Success(model)

        verify { savedStateHandle.get<String>(MOVIE_ID) }
        // Given
        coEvery { detailUseCase(any()) } returns data
        Assert.assertEquals(
            detailScreenViewModel.viewState.value,
            DetailScreenContract.State.Loading
        )

        // When && Assertion
        detailScreenViewModel.handleEvent(DetailScreenContract.Event.OnDetailFetch)
        delay(100)
        Assert.assertEquals(
            detailScreenViewModel.viewState.value,
            DetailScreenContract.State.Success(
                model
            )
        )

        Assert.assertEquals(
            (detailScreenViewModel.viewState.value as DetailScreenContract.State.Success).model,
            model
        )
        //Then
        coVerify { detailUseCase(any()) }

    }


    @Test
    fun test_fetch_movie_detail_failure() = runTest {

        val data = Resource.Error(Exception(NO_DATA_FOUND))
        // Given
        coEvery { detailUseCase.invoke(any()) } returns data
        Assert.assertEquals(
            detailScreenViewModel.viewState.value,
            DetailScreenContract.State.Loading
        )

        // When && Assertions
        detailScreenViewModel.handleEvent(DetailScreenContract.Event.OnDetailFetch)
        delay(100)
        Assert.assertEquals(
            detailScreenViewModel.viewState.value,
            DetailScreenContract.State.Error(
                data.exception.message.toString()
            )
        )

        Assert.assertEquals(
            (detailScreenViewModel.viewState.value as DetailScreenContract.State.Error).message,
            data.exception.message.toString()
        )

        // Then
        coVerify { detailUseCase.invoke(any()) }
    }

    private companion object {
        private const val ID = "id"
        private const val NO_DATA_FOUND = "No data found"
    }

}