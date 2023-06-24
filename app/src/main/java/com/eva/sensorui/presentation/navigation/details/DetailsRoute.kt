package com.eva.sensorui.presentation.navigation.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eva.sensorui.domain.models.BaseSensorInfoModel
import com.eva.sensorui.presentation.composables.SensorCardDetailed
import com.eva.sensorui.presentation.composables.SensorGraph
import com.eva.sensorui.utils.AxisInformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsRoute(
    navController: NavController,
    axis: AxisInformation,
    sensorInfo: BaseSensorInfoModel?,
    sensorValues: List<AxisInformation>,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (sensorInfo != null) {
                        Text(text = sensorInfo.name + " SENSOR")
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
                }, colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    ) { scPadding ->
        AnimatedVisibility(
            visible = sensorInfo != null,
            enter = fadeIn()
        ) {
            Column(
                modifier = modifier
                    .padding(scPadding)
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(.7f)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "The graph is responsive only when the values changes",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 6.dp)
                    )
                    SensorGraph(
                        sensorValues = sensorValues,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(4.dp)
                        .align(Alignment.End)

                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                append("X: ")
                            }
                            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                append("Time")
                            }
                        }
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                append("Y:")
                            }
                            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                append(" Sensor Values")
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                SensorCardDetailed(
                    sensor = sensorInfo!!,
                    axis = axis,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}