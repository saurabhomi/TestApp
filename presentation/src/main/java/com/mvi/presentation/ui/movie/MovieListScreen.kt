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
import com.mvi.domain.model.movie.MovieDomainModel
import com.mvi.presentation.R
import com.mvi.presentation.contract.MovieListScreenContract
import com.mvi.presentation.ui.common.ProgressIndicator
import com.mvi.presentation.ui.common.Retry
import kotlinx.coroutines.launch

@Composable
fun MovieListScreen(
    state: MovieListScreenContract.State,
    onEventSent: (event: MovieListScreenContract.Event) -> Unit,
    onNavigationRequested: (MovieDomainModel.MovieListDomainModel) -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val snackBarSuccessMessage = stringResource(R.string.data_loaded_message)
    LaunchedEffect(state) {
        when (state) {
            is MovieListScreenContract.State.Error -> {
                scope.launch {
                    showSnackBar(snackBarHostState, state.errorMessage)
                }
            }

            is MovieListScreenContract.State.Success -> {
                scope.launch {
                    showSnackBar(snackBarHostState, snackBarSuccessMessage)
                }
            }

            is MovieListScreenContract.State.Loading -> {
                onEventSent(MovieListScreenContract.Event.OnListFetch)
            }
        }
    }

    Scaffold(snackbarHost = {
        SnackbarHost(snackBarHostState)
    }, topBar = { HomeTopAppBar() }) {
        when (state) {
            is MovieListScreenContract.State.Loading -> ProgressIndicator()
            is MovieListScreenContract.State.Success -> {
                MovieList(
                    paddingValues = it, state.data.result,
                ) { model ->
                    onNavigationRequested(model)
                }
            }

            is MovieListScreenContract.State.Error -> {
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
