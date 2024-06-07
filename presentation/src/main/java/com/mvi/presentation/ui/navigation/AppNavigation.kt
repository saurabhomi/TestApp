package com.mvi.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mvi.common.constants.Constants.Companion.Args.MOVIE_ID

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Navigation.Routes.MOVIES
    ) {
        composable(
            route = Navigation.Routes.MOVIES
        ) {
            MovieScreenDestination(navController)
        }

        composable(
            route = Navigation.Routes.DETAILS,
        ) {
            DetailScreenDestination(
                navController
            )
        }
    }
}

object Navigation {


    object Routes {
        const val MOVIES = "movie"
        const val DETAILS = "$MOVIES/{$MOVIE_ID}"
    }

}

fun NavController.navigateToDetails(id: String) {
    navigate(route = "${Navigation.Routes.MOVIES}/${id}")
}
