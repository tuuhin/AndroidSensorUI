package com.eva.sensorui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eva.sensorui.presentation.navigation.details.DetailsRoute
import com.eva.sensorui.presentation.navigation.home.HomeRoute
import com.eva.sensorui.presentation.navigation.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigationGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.Home.location,
        modifier = modifier
    ) {
        composable(Screens.Home.location) {
            val viewModel = koinViewModel<HomeViewModel>()
            val availableSensors by viewModel.availableSensors.collectAsStateWithLifecycle()

            HomeRoute(navController = navController, sensors = availableSensors)
        }
        composable(Screens.Detailed.location) {
            DetailsRoute(navController = navController)
        }

    }
}