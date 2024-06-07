package com.mvi.presentation.contract

import com.mvi.base.UiEffect
import com.mvi.base.UiEvent
import com.mvi.base.UiState
import com.mvi.presentation.model.detail.DetailUiModel

class DetailScreenContract {

    sealed class Event : UiEvent {
        data object BackButtonClicked : Event()
        data object OnDetailFetch : Event()
        data object OnDetailLoaded : Event()
        data class OnError(val message: String) : Event()
    }

    sealed class State : UiState {
        data object Idle : State()
        data object Loading : State()
        data class Success(val model: DetailUiModel) : State()
        data class Error(val message: String) : State()
    }

    sealed class Effect : UiEffect {
        data class ShowError(val message: String) : Effect()
        data object ShowSuccess : Effect()
        sealed class Navigation : Effect() {
            data object Back : Navigation()
        }
    }
}