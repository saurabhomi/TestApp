package com.mvi.presentation.viewmodel.splash

import androidx.lifecycle.viewModelScope
import com.mvi.base.BaseViewModel
import com.mvi.base.UiEffect
import com.mvi.presentation.contract.SplashContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor() :
    BaseViewModel<SplashContract.Event, SplashContract.State, UiEffect>() {
    override fun createInitialState(): SplashContract.State {
        return SplashContract.State.Loading
    }

    override fun handleEvent(event: SplashContract.Event) {
        viewModelScope.launch {
            delay(2000)
            setState {
                SplashContract.State.Idle
            }
        }
    }

}