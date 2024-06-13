package com.mvi.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mvi.presentation.ui.movie.MovieListScreen
import com.mvi.presentation.viewmodel.movie.MovieScreenViewModel


@Composable
fun MovieScreenDestination(navController: NavController) {
    val viewModel = hiltViewModel<MovieScreenViewModel>()

    MovieListScreen(state = viewModel.viewState.collectAsState().value,
        onEventSent = { event -> viewModel.handleEvent(event) },
        onNavigationRequested = { model ->
            navController.navigateToDetails(model.id.toString())
        })
}