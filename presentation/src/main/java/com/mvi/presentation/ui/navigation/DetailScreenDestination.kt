package com.mvi.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mvi.presentation.ui.detail.DetailScreen
import com.mvi.presentation.viewmodel.detail.DetailScreenViewModel

@Composable
fun DetailScreenDestination(navController: NavController) {
    val viewModel = hiltViewModel<DetailScreenViewModel>()

    DetailScreen(state = viewModel.viewState.collectAsState().value,
        onEventSent = { event -> viewModel.handleEvent(event) },
        onNavigationRequested = {
            navController.popBackStack()
        })
}