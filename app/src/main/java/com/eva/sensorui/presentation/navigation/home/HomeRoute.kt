package com.eva.sensorui.presentation.navigation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eva.sensorui.R
import com.eva.sensorui.domain.models.BaseSensorInfoModel
import com.eva.sensorui.presentation.composables.SensorCard

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeRoute(
    navController: NavController,
    sensors: List<BaseSensorInfoModel>,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Sensors",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    ) { scPadding ->
        LazyColumn(
            modifier = modifier
                .padding(scPadding)
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                    Image(
                        painter = painterResource(id = R.drawable.temperature_control),
                        contentDescription = "A simple sensor logo",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surfaceTint)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Shows the basic sensor data on your mobile",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "Available Sensors",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "These are the available sensors on your phone",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

            }
            itemsIndexed(sensors) { idx, sensor ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItemPlacement()
                ) {
                    Text(
                        text = "${idx + 1}.",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .weight(0.1f)
                            .padding(horizontal = 2.dp)
                    )
                    SensorCard(
                        image = sensor.imageRes,
                        title = sensor.name,
                        range = sensor.range,
                        vendor = sensor.vendor,
                        onTap = { navController.navigate("/detailed/" + sensor.sensorType) },
                        modifier = Modifier.weight(0.9f)
                    )
                }
            }
        }
    }
}
