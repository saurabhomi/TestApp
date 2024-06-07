package com.mvi.presentation.contract

import com.mvi.base.UiEvent
import com.mvi.base.UiState

class SplashContract {


    sealed class Event : UiEvent {
        data object Close : Event()
    }

    sealed class State : UiState {
        data object Idle : State()
        data object Loading : State()
    }

}