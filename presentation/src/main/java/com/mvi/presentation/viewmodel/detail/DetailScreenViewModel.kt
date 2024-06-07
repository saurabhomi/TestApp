package com.mvi.presentation.viewmodel.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mvi.base.BaseViewModel
import com.mvi.base.Resource
import com.mvi.common.constants.Constants.Companion.Args.MOVIE_ID
import com.mvi.domain.usecase.GetMovieDetailUseCase
import com.mvi.presentation.contract.DetailScreenContract
import com.mvi.presentation.mapper.detail.DetailDomainUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val detailUseCase: GetMovieDetailUseCase,
    private val detailMapper: DetailDomainUiMapper,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<DetailScreenContract.Event, DetailScreenContract.State, DetailScreenContract.Effect>() {

    private lateinit var id: String

    init {
        savedStateHandle.get<String>(MOVIE_ID)?.let {
            id = it
        }
    }

    override fun createInitialState(): DetailScreenContract.State {
        return DetailScreenContract.State.Idle
    }

    override fun handleEvent(event: DetailScreenContract.Event) {
        when (event) {
            DetailScreenContract.Event.OnDetailFetch -> {
                fetchMovieList(id)
            }

            DetailScreenContract.Event.BackButtonClicked -> {
                setEffect { DetailScreenContract.Effect.Navigation.Back }
            }

            DetailScreenContract.Event.OnDetailLoaded -> {
                setEffect { DetailScreenContract.Effect.ShowSuccess }
            }

            is DetailScreenContract.Event.OnError -> {
                setEffect { DetailScreenContract.Effect.ShowError(event.message) }
            }
        }
    }

    private fun fetchMovieList(id: String) {
        viewModelScope.launch {
            setState { DetailScreenContract.State.Loading }
            detailUseCase.invoke(id).let {
                when (it) {
                    is Resource.Success -> {
                        setState {
                            DetailScreenContract.State.Success(
                                detailMapper.from(it.data)
                            )
                        }
                        // Set State
                    }

                    is Resource.Error -> {
                        // Set Effect
                        setState { DetailScreenContract.State.Error(it.exception.message.toString()) }
                    }
                }
            }
        }
    }

}