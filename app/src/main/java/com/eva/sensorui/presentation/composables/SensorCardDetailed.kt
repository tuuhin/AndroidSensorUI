package com.eva.sensorui.presentation.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.eva.sensorui.domain.models.BaseSensorInfoModel
import com.eva.sensorui.utils.AxisInformation

@Composable
fun SensorCardDetailed(
    sensor: BaseSensorInfoModel, axis: AxisInformation, modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier.clip(MaterialTheme.shapes.medium), shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
            ) {
                Image(
                    painter = painterResource(id = sensor.imageRes),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surfaceTint),
                    modifier = Modifier
                        .size(50.dp)
                        .padding(4.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = "Current :",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = sensor.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
                when (axis) {
                    is AxisInformation.XAxisInformation -> {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(4.dp)
                                    .border(BorderStroke(1.dp, Color.Black))
                                    .background(Color.Red)
                            )
                            Text(text = "${axis.x}")
                        }
                    }

                    is AxisInformation.XYAxisInformation -> {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(4.dp)
                                    .border(BorderStroke(1.dp, Color.Black))
                                    .background(Color.Red)
                            )
                            Text(
                                text = "${axis.x}",
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .size(4.dp)
                                    .border(BorderStroke(1.dp, Color.Black))
                                    .background(Color.Green)
                            )
                            Text(text = "${axis.y}")
                        }
                    }

                    is AxisInformation.XYZAxisInformation -> {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .border(BorderStroke(1.dp, Color.Black))
                                    .background(MaterialTheme.colorScheme.primary)
                            )
                            Text(
                                text = "${axis.x}",
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .border(BorderStroke(1.dp, Color.Black))
                                    .background(MaterialTheme.colorScheme.secondary)
                            )
                            Text(
                                text = "${axis.y}",
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .border(BorderStroke(1.dp, Color.Black))
                                    .background(MaterialTheme.colorScheme.tertiary)
                            )
                            Text(
                                text = "${axis.z}",
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )
                        }
                    }

                    else -> Text(text = "Unknown")
                }
            }
        }
        Divider(modifier = Modifier.padding(horizontal = 10.dp))
        Column(
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            SensorExtraData(title = "Vendor", value = sensor.vendor)
            SensorExtraData(title = "Range", value = "${sensor.range}")
            SensorExtraData(title = "Power", value = "${sensor.power}")
            SensorExtraData(title = "Version", value = "${sensor.version}")
        }
    }
}