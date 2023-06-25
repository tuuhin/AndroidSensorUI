package com.eva.sensorui.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.eva.sensorui.presentation.navigation.details.DetailedRouteViewModel
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

            HomeRoute(
                navController = navController,
                sensors = availableSensors
            )
        }
        composable(
            Screens.Detailed.location + NavArgs.SENSOR_ID_PARAM,
            arguments = listOf(
                navArgument(NavArgs.SENSOR_ID) {
                    defaultValue = -1
                    type = NavType.IntType
                }
            )
        ) {

            val viewModel = koinViewModel<DetailedRouteViewModel>()
            val sensorInfo by viewModel.sensorInformation.collectAsStateWithLifecycle()
            val currentValue by viewModel.currentSensorValue.collectAsStateWithLifecycle()
            val sensorGraphData by viewModel.sensorGraphData.collectAsStateWithLifecycle()

            if (sensorInfo == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                DetailsRoute(
                    navController = navController,
                    axis = currentValue,
                    sensorInfo = sensorInfo!!,
                    sensorGraphData = sensorGraphData
                )
            }
        }

    }
}