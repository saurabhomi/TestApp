package com.mvi.presentation

import androidx.test.filters.SmallTest
import com.mvi.presentation.contract.SplashContract
import com.mvi.presentation.viewmodel.splash.SplashViewModel
import io.mockk.MockKAnnotations
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
class SplashViewModelTest {
    private val dispatcher: CoroutineDispatcher = StandardTestDispatcher()
    private lateinit var splashViewModel: SplashViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(
            this
        ) // turn relaxUnitFun on for all mocks
        // Create MainViewModel before every test
        splashViewModel = SplashViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cancel()
    }

    @Test
    fun test_fetch_movie_list_success() = runTest {
        // Given
        Assert.assertEquals(
            splashViewModel.viewState.value, SplashContract.State.Loading
        )
        // When && Assertions
        splashViewModel.handleEvent(SplashContract.Event.Close)
        delay(2100)
        //Then
        Assert.assertEquals(
            splashViewModel.viewState.value, SplashContract.State.Idle
        )


    }
}