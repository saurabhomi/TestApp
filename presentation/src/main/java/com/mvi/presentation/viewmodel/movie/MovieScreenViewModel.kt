package com.mvi.presentation.viewmodel.movie


import androidx.lifecycle.viewModelScope
import com.mvi.base.BaseViewModel
import com.mvi.base.Resource
import com.mvi.domain.usecase.GetMovieListUseCase
import com.mvi.presentation.contract.MovieListScreenContract
import com.mvi.presentation.mapper.movie.MovieListDomainUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieScreenViewModel @Inject constructor(
    private val movieListUseCase: GetMovieListUseCase,
    private val movieListMapper: MovieListDomainUiMapper
) : BaseViewModel<MovieListScreenContract.Event, MovieListScreenContract.State, MovieListScreenContract.Effect>() {
    override fun createInitialState(): MovieListScreenContract.State {
        return MovieListScreenContract.State.Idle
    }

    override fun handleEvent(event: MovieListScreenContract.Event) {
        when (event) {
            is MovieListScreenContract.Event.OnListFetch -> {
                fetchMovieList()
            }

            is MovieListScreenContract.Event.OnListLoaded -> {
                setEffect { MovieListScreenContract.Effect.ShowSuccess }
            }

            is MovieListScreenContract.Event.OnListItemClick -> {
                setEffect {
                    MovieListScreenContract.Effect.Navigation.ToDetail(
                        event.model.id.toString()
                    )
                }
            }

            is MovieListScreenContract.Event.OnError -> setEffect {
                MovieListScreenContract.Effect.ShowError(event.errorMessage)
            }
        }

    }


    private fun fetchMovieList() {
        viewModelScope.launch {
            setState { MovieListScreenContract.State.Loading }
            movieListUseCase().let {
                when (it) {
                    is Resource.Success -> {
                        setState {
                            MovieListScreenContract.State.Success(
                                movieListMapper.from(it.data)
                            )
                        }
                    }

                    is Resource.Error -> {
                        // Set Effect
                        setState {
                            MovieListScreenContract.State.Error(
                                it.exception.message.toString()
                            )
                        }
                    }
                }
            }
        }
    }
}