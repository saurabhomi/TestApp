package com.mvi.presentation.contract

import com.mvi.domain.model.detail.DetailDomainModel
import com.mvi.presentation.UiEvent
import com.mvi.presentation.UiState

class DetailScreenContract {

    sealed class Event : UiEvent {
        data object OnDetailFetch : Event()
    }

    sealed class State : UiState {
        data object Loading : State()
        data class Success(val model: DetailDomainModel) : State()
        data class Error(val message: String) : State()
    }
}