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
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
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
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
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
        MockKAnnotations.init(this)
        every { savedStateHandle.get<String>(MOVIE_ID) } returns ID
        detailScreenViewModel =
            DetailScreenViewModel(detailUseCase, savedStateHandle)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cancel()
        unmockkAll()
    }

    @Test
    fun `Given successful use case response When fetching movie detail Then show success state with data`() =
        runTest {
            val detailModel = TestDataGenerator.generateMovieDetailData()
            val success = Resource.Success(detailModel)

            verify { savedStateHandle.get<String>(MOVIE_ID) }

            coEvery { detailUseCase(any()) } returns success

            expectThat(detailScreenViewModel.viewState.value).isA<DetailScreenContract.State.Loading>()

            detailScreenViewModel.handleEvent(DetailScreenContract.Event.OnDetailFetch)
            delay(100)

            expectThat(detailScreenViewModel.viewState.value).isA<DetailScreenContract.State.Success>()
                .get { model }.isEqualTo(detailModel)

        }

    @Test
    fun `Given error from use case When fetching movie detail Then show error state with message`() =
        runTest {
            val data = Resource.Error(Exception(NO_DATA_FOUND))

            coEvery { detailUseCase.invoke(any()) } returns data

            expectThat(detailScreenViewModel.viewState.value).isA<DetailScreenContract.State.Loading>()

            detailScreenViewModel.handleEvent(DetailScreenContract.Event.OnDetailFetch)
            delay(100)

            expectThat(detailScreenViewModel.viewState.value).isA<DetailScreenContract.State.Error>()
                .get { message }.isEqualTo(data.exception.message.toString())
        }

    private companion object {
        private const val ID = "id"
        private const val NO_DATA_FOUND = "No data found"
    }
}
