package com.eva.sensorui.presentation.navigation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eva.sensorui.domain.models.BaseSensorInfoModel
import com.eva.sensorui.presentation.composables.SensorCard

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeRoute(
    navController: NavController,
    sensors: List<BaseSensorInfoModel>,
    modifier: Modifier = Modifier
) {
    Scaffold {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Text(text = "Available Sensors", style = MaterialTheme.typography.titleLarge)
            Divider(modifier = Modifier.padding(2.dp))
            LazyColumn(
                modifier = Modifier.padding(PaddingValues(top = 8.dp)),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                itemsIndexed(sensors) { _, sensor ->
                    SensorCard(
                        image = sensor.imageRes,
                        title = sensor.name,
                        onTap = { /*TODO*/ },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}