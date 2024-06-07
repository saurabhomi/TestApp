package com.mvi.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.test.filters.SmallTest
import com.mvi.base.Resource
import com.mvi.common.constants.Constants.Companion.Args.MOVIE_ID
import com.mvi.common.constants.Constants.Companion.NO_DATA_FOUND
import com.mvi.domain.usecase.GetMovieDetailUseCase
import com.mvi.presentation.contract.DetailScreenContract
import com.mvi.presentation.mapper.detail.DetailDomainUiMapper
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

    private val detailDomainUiMapper = DetailDomainUiMapper()

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
        // turn relaxUnitFun on for all mocks
        // Create ViewModel before every test
        detailScreenViewModel = DetailScreenViewModel(
            detailUseCase, detailDomainUiMapper, savedStateHandle
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
            DetailScreenContract.State.Idle
        )

        // When && Assertion
        detailScreenViewModel.setEvent(DetailScreenContract.Event.OnDetailFetch)
        delay(100)
        Assert.assertEquals(
            detailScreenViewModel.viewState.value,
            DetailScreenContract.State.Success(
                detailDomainUiMapper.from(model)
            )
        )

        detailScreenViewModel.setEvent(DetailScreenContract.Event.OnDetailLoaded)

        Assert.assertEquals(
            (detailScreenViewModel.viewState.value as DetailScreenContract.State.Success).model,
            detailDomainUiMapper.from(
                model
            )
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
            DetailScreenContract.State.Idle
        )


        // When && Assertions
        detailScreenViewModel.setEvent(DetailScreenContract.Event.OnDetailFetch)
        delay(100)
        Assert.assertEquals(
            detailScreenViewModel.viewState.value,
            DetailScreenContract.State.Error(
                data.exception.message.toString()
            )
        )

        detailScreenViewModel.setEvent(
            DetailScreenContract.Event.OnError(
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

    @Test
    fun test_fetch_movie_detail_navigate_back() = runTest {

        val model = TestDataGenerator.generateMovieDetailData()
        val data = Resource.Success(model)

        //Given
        verify { savedStateHandle.get<String>(MOVIE_ID) }
        coEvery { detailUseCase.invoke(any()) } returns data
        Assert.assertEquals(
            detailScreenViewModel.viewState.value,
            DetailScreenContract.State.Idle
        )

        // When && Assert.assertEqualsions
        detailScreenViewModel.setEvent(DetailScreenContract.Event.OnDetailFetch)
        delay(100)
        Assert.assertEquals(
            detailScreenViewModel.viewState.value,
            DetailScreenContract.State.Success(
                detailDomainUiMapper.from(model)
            )
        )

        detailScreenViewModel.setEvent(DetailScreenContract.Event.OnDetailLoaded)

        Assert.assertEquals(
            (detailScreenViewModel.viewState.value as DetailScreenContract.State.Success).model,
            detailDomainUiMapper.from(
                model
            )
        )

        detailScreenViewModel.setEvent(
            DetailScreenContract.Event.BackButtonClicked
        )

        // Then
        coVerify { detailUseCase.invoke(any()) }
    }

    private companion object {
        private const val ID = "id"
    }

}