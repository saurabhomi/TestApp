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
import com.mvi.presentation.R
import com.mvi.presentation.contract.DetailScreenContract
import com.mvi.presentation.ui.common.ProgressIndicator
import com.mvi.presentation.ui.common.Retry
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(
    state: DetailScreenContract.State,
    onEventSent: (event: DetailScreenContract.Event) -> Unit,
    onNavigationRequested: () -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val snackBarSuccessMessage = stringResource(R.string.data_loaded_message)

    LaunchedEffect(state) {
        when (state) {
            is DetailScreenContract.State.Error ->
                scope.launch {
                    showSnackBar(snackBarHostState, state.message)
                }

            is DetailScreenContract.State.Success -> scope.launch {
                showSnackBar(snackBarHostState, snackBarSuccessMessage)
            }

            DetailScreenContract.State.Loading -> onEventSent(DetailScreenContract.Event.OnDetailFetch)
        }
    }

    Scaffold(snackbarHost = {
        SnackbarHost(snackBarHostState)
    }, topBar = {
        DetailScreenTopAppBar {
            onNavigationRequested()
        }
    }) {
        when (state) {
            is DetailScreenContract.State.Success -> {
                MovieDetail(
                    it, state.model
                )
            }

            is DetailScreenContract.State.Error -> {
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
