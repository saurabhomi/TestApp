package com.mvi.presentation.ui.movie

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import com.mvi.common.constants.Constants.Companion.EFFECT_KEY
import com.mvi.presentation.R
import com.mvi.presentation.contract.MovieListScreenContract
import com.mvi.presentation.ui.common.ProgressIndicator
import com.mvi.presentation.ui.common.Retry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun MovieListScreen(
    state: MovieListScreenContract.State,
    effectFlow: Flow<MovieListScreenContract.Effect>,
    onEventSent: (event: MovieListScreenContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: MovieListScreenContract.Effect.Navigation) -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val snackBarSuccessMessage = stringResource(R.string.data_loaded_message)
    LaunchedEffect(EFFECT_KEY) {
        effectFlow.onEach { effect ->
            when (effect) {
                is MovieListScreenContract.Effect.ShowError -> {
                    scope.launch {
                        showSnackBar(snackBarHostState, effect.message)
                    }
                }

                is MovieListScreenContract.Effect.ShowSuccess -> {
                    scope.launch {
                        showSnackBar(snackBarHostState, snackBarSuccessMessage)
                    }
                }

                is MovieListScreenContract.Effect.Navigation.ToDetail -> {
                    onNavigationRequested(effect)
                }
            }
        }.collect()

    }

    Scaffold(snackbarHost = {
        SnackbarHost(snackBarHostState)
    }, topBar = { HomeTopAppBar() }) {
        when (state) {
            is MovieListScreenContract.State.Idle -> {
                onEventSent(MovieListScreenContract.Event.OnListFetch)

            }

            is MovieListScreenContract.State.Loading -> ProgressIndicator()
            is MovieListScreenContract.State.Success -> {
                LaunchedEffect(EFFECT_KEY) {
                    onEventSent(MovieListScreenContract.Event.OnListLoaded)
                }
                MovieList(
                    paddingValues = it, state.data.result!!,
                ) { model ->
                    onEventSent(
                        MovieListScreenContract.Event.OnListItemClick(
                            model
                        )
                    )
                }
            }

            is MovieListScreenContract.State.Error -> {
                onEventSent(MovieListScreenContract.Event.OnError(state.errorMessage))
                Retry {
                    onEventSent(MovieListScreenContract.Event.OnListFetch)
                }
            }
        }
    }
}


private suspend fun showSnackBar(
    snackBarHostState: SnackbarHostState, msg: String
) {
    snackBarHostState.showSnackbar(
        message = msg, duration = SnackbarDuration.Short
    )

}
