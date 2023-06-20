package com.eva.sensorui.presentation.navigation.details

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eva.sensorui.domain.models.BaseSensorInfoModel
import com.eva.sensorui.presentation.composables.SensorCardDetailed
import com.eva.sensorui.utils.AxisInformation

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsRoute(
    navController: NavController,
    axis: AxisInformation,
    sensorInfo: BaseSensorInfoModel?,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (sensorInfo != null) {
                        Text(text = sensorInfo.name)
                    }
                },
                navigationIcon = {
                    if (navController.previousBackStackEntry != null)
                        IconButton(
                            onClick = { navController.navigateUp() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Go back"
                            )
                        }
                }
            )
        }
    ) { scPadding ->
        AnimatedVisibility(
            visible = sensorInfo != null
        ) {
            Column(
                modifier = modifier
                    .padding(scPadding)
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                SensorCardDetailed(
                    sensor = sensorInfo!!,
                    axis = axis
                )
            }
        }
    }
}