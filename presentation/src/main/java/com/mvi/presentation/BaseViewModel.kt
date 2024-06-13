package com.mvi.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Base class for [ViewModel] instances
 */
abstract class BaseViewModel<Event : UiEvent, ViewState : UiState> :
    ViewModel() {
    abstract fun createInitialState(): ViewState

    private val initialState: ViewState by lazy { createInitialState() }

    private val _viewState: MutableStateFlow<ViewState> =
        MutableStateFlow(initialState)
    val viewState = _viewState.asStateFlow()
    protected fun setState(reducer: ViewState.() -> ViewState) {
        val newState = viewState.value.reducer()
        _viewState.value = newState
    }
}