package com.mvi.presentation.contract

import com.mvi.domain.model.movie.MovieDomainModel
import com.mvi.presentation.UiEvent
import com.mvi.presentation.UiState

class MovieListScreenContract {
    sealed class Event : UiEvent {
        data object OnListFetch : Event()
    }

    sealed class State : UiState {
        data object Loading : State()
        data class Success(val data: MovieDomainModel) : State()
        data class Error(val errorMessage: String) : State()
    }
}