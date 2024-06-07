package com.mvi.presentation.contract

import com.mvi.base.UiEffect
import com.mvi.base.UiEvent
import com.mvi.base.UiState
import com.mvi.presentation.model.movie.MovieListUiModel
import com.mvi.presentation.model.movie.MovieUiModel

class MovieListScreenContract {


    sealed class Event : UiEvent {
        data object OnListFetch : Event()
        data object OnListLoaded : Event()
        data class OnListItemClick(val model: MovieListUiModel) : Event()
        data class OnError(val errorMessage: String) : Event()
    }

    sealed class State : UiState {
        data object Idle : State()
        data object Loading : State()
        data class Success(val data: MovieUiModel) : State()
        data class Error(val errorMessage: String) : State()
    }

    sealed class Effect : UiEffect {
        data class ShowError(val message: String) : Effect()
        data object ShowSuccess : Effect()

        sealed class Navigation : Effect() {
            data class ToDetail(val id: String) : Navigation()
        }
    }
}