package com.mvi.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mvi.presentation.contract.DetailScreenContract
import com.mvi.presentation.viewmodel.detail.DetailScreenViewModel
import com.mvi.presentation.ui.detail.DetailScreen

@Composable
fun DetailScreenDestination(navController: NavController) {
    val viewModel = hiltViewModel<DetailScreenViewModel>()


    DetailScreen(state = viewModel.viewState.collectAsState().value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            if (navigationEffect is DetailScreenContract.Effect.Navigation.Back) {
                navController.popBackStack()
            }
        })
}