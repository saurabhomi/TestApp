package com.mvi.presentation.viewmodel.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mvi.common.Resource
import com.mvi.domain.usecase.GetMovieDetailUseCase
import com.mvi.presentation.BaseViewModel
import com.mvi.presentation.Constants.Companion.Args.MOVIE_ID
import com.mvi.presentation.contract.DetailScreenContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val detailUseCase: GetMovieDetailUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<DetailScreenContract.Event, DetailScreenContract.State>() {

    private var id: String

    init {
        savedStateHandle.get<String>(MOVIE_ID).let {
            id = it!!
        }
    }

    override fun createInitialState(): DetailScreenContract.State {
        return DetailScreenContract.State.Loading
    }

    fun handleEvent(event: DetailScreenContract.Event) {
        when (event) {
            DetailScreenContract.Event.OnDetailFetch -> {
                setState {
                    DetailScreenContract.State.Loading
                }
                fetchMovieList(id)
            }
        }
    }

    private fun fetchMovieList(id: String) {
        viewModelScope.launch {
            when (val data = detailUseCase(id)) {
                is Resource.Success -> {
                    setState {
                        DetailScreenContract.State.Success(
                            data.data
                        )
                    }
                }

                is Resource.Error -> {
                    setState { DetailScreenContract.State.Error(data.exception.message.toString()) }
                }
            }
        }
    }

}