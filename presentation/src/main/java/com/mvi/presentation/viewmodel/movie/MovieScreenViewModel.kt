package com.mvi.presentation.viewmodel.movie


import androidx.lifecycle.viewModelScope
import com.mvi.common.Resource
import com.mvi.domain.usecase.GetMovieListUseCase
import com.mvi.presentation.BaseViewModel
import com.mvi.presentation.contract.MovieListScreenContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieScreenViewModel @Inject constructor(
    private val movieListUseCase: GetMovieListUseCase,
) : BaseViewModel<MovieListScreenContract.Event, MovieListScreenContract.State>() {
    override fun createInitialState(): MovieListScreenContract.State {
        return MovieListScreenContract.State.Loading
    }

    fun handleEvent(event: MovieListScreenContract.Event) {
        when (event) {
            is MovieListScreenContract.Event.OnListFetch -> {
                setState {
                    MovieListScreenContract.State.Loading
                }
                fetchMovieList()
            }
        }

    }


    private fun fetchMovieList() {
        viewModelScope.launch {
            when (val data = movieListUseCase()) {
                is Resource.Success -> {
                    setState {
                        MovieListScreenContract.State.Success(
                            data.data
                        )
                    }
                }

                is Resource.Error -> {
                    // Set Effect
                    setState {
                        MovieListScreenContract.State.Error(
                            data.exception.message.toString()
                        )
                    }
                }
            }
        }
    }
}