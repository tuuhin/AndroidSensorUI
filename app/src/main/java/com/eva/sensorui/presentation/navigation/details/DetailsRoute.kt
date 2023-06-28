package com.eva.sensorui.presentation.navigation.details

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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eva.sensorui.R
import com.eva.sensorui.domain.models.BaseSensorInfoModel
import com.eva.sensorui.domain.models.GraphData
import com.eva.sensorui.presentation.composables.SensorCardDetailed
import com.eva.sensorui.presentation.composables.SensorGraph
import com.eva.sensorui.presentation.utils.UiEvents
import com.eva.sensorui.utils.AxisInformation
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsRoute(
    navController: NavController,
    axis: AxisInformation,
    sensorInfo: BaseSensorInfoModel,
    sensorGraphData: GraphData,
    errors: Flow<UiEvents>,
    modifier: Modifier = Modifier,
) {

    val snackBarState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        errors.collect { events ->
            when (events) {
                is UiEvents.ShowSnackBar -> snackBarState.showSnackbar(events.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarState) },
        topBar = {
            TopAppBar(
                title = { Text(text = sensorInfo.name) },
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

        Column(
            modifier = modifier
                .padding(scPadding)
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(.7f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.graph_info),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.tertiary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 6.dp)
                )
                SensorGraph(
                    sensorValues = sensorGraphData.indices,
                    maximumRange = sensorGraphData.max,
                    minimumRange = sensorGraphData.min,
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
                sensor = sensorInfo,
                axis = axis,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}
