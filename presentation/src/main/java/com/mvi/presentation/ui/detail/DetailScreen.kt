package com.mvi.presentation.ui.detail

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
import com.mvi.presentation.contract.DetailScreenContract
import com.mvi.presentation.ui.common.ProgressIndicator
import com.mvi.presentation.ui.common.Retry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(
    state: DetailScreenContract.State,
    effectFlow: Flow<DetailScreenContract.Effect>,
    onEventSent: (event: DetailScreenContract.Event) -> Unit,
    onNavigationRequested: (DetailScreenContract.Effect.Navigation) -> Unit
) {

    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val snackBarSuccessMessage = stringResource(R.string.data_loaded_message)

    LaunchedEffect(EFFECT_KEY) {
        effectFlow.onEach { effect ->
            when (effect) {
                DetailScreenContract.Effect.Navigation.Back -> {
                    onNavigationRequested(DetailScreenContract.Effect.Navigation.Back)
                }

                is DetailScreenContract.Effect.ShowError -> {
                    scope.launch {
                        showSnackBar(snackBarHostState, effect.message)
                    }
                }

                is DetailScreenContract.Effect.ShowSuccess -> {
                    scope.launch {
                        showSnackBar(snackBarHostState, snackBarSuccessMessage)
                    }
                }
            }
        }.collect()
    }

    Scaffold(snackbarHost = {
        SnackbarHost(snackBarHostState)
    }, topBar = {
        DetailScreenTopAppBar {
            onEventSent(DetailScreenContract.Event.BackButtonClicked)
        }
    }) {
        when (state) {
            is DetailScreenContract.State.Idle -> {
                onEventSent(DetailScreenContract.Event.OnDetailFetch)
            }

            is DetailScreenContract.State.Success -> {
                onEventSent(DetailScreenContract.Event.OnDetailLoaded)
                MovieDetail(
                    it, state.model
                )
            }

            is DetailScreenContract.State.Error -> {
                onEventSent(
                    DetailScreenContract.Event.OnError(
                        state.message
                    )
                )
                Retry {
                    onEventSent(DetailScreenContract.Event.OnDetailFetch)
                }
            }

            DetailScreenContract.State.Loading -> ProgressIndicator()
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
