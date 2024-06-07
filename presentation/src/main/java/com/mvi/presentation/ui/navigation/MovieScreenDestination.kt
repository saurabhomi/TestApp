package com.mvi.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mvi.presentation.contract.MovieListScreenContract
import com.mvi.presentation.viewmodel.movie.MovieScreenViewModel
import com.mvi.presentation.ui.movie.MovieListScreen


@Composable
fun MovieScreenDestination(navController: NavController) {
    val viewModel = hiltViewModel<MovieScreenViewModel>()

    MovieListScreen(state = viewModel.viewState.collectAsState().value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            if (navigationEffect is MovieListScreenContract.Effect.Navigation.ToDetail) {
                navController.navigateToDetails(navigationEffect.id)
            }
        })
}