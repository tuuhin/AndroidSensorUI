package com.eva.sensorui.presentation.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.eva.sensorui.utils.AxisInformation
import java.text.DecimalFormat

@Composable
fun AxisPresenter(
    axis: AxisInformation,
    modifier: Modifier = Modifier
) {

    val formatter = remember {
        DecimalFormat("##.###")
    }

    when (axis) {
        is AxisInformation.XAxisInformation -> {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .border(BorderStroke(1.dp, Color.Black))
                        .background(MaterialTheme.colorScheme.primary)
                )
                Text(
                    text = formatter.format(axis.x),
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
        }

        is AxisInformation.XYAxisInformation -> {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .border(BorderStroke(1.dp, Color.Black))
                        .background(MaterialTheme.colorScheme.primary)
                )
                Text(
                    text = "x: ${formatter.format(axis.x)}",
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .border(BorderStroke(1.dp, Color.Black))
                        .background(MaterialTheme.colorScheme.secondary)
                )
                Text(
                    text = "y: ${formatter.format(axis.y)}",
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
        }

        is AxisInformation.XYZAxisInformation -> {

            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .border(BorderStroke(1.dp, Color.Black))
                        .background(MaterialTheme.colorScheme.primary)
                )
                Text(
                    text = "x: ${formatter.format(axis.x)}",
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .border(BorderStroke(1.dp, Color.Black))
                        .background(MaterialTheme.colorScheme.secondary)
                )
                Text(
                    text = "y: ${formatter.format(axis.y)}",
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
                    text = "z: ${formatter.format(axis.z)}",
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
        }

        else -> Text(text = "Unknown")
    }
}