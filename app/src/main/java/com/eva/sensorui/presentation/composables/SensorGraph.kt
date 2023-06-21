package com.eva.sensorui.presentation.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eva.sensorui.utils.AxisInformation

@OptIn(ExperimentalTextApi::class)
@Composable
fun SensorGraph(
    sensorValues: List<AxisInformation>,
    modifier: Modifier = Modifier
) {

    val textMeasurer = rememberTextMeasurer()

    val colorPrimary = MaterialTheme.colorScheme.onPrimaryContainer
    val colorSecondary = MaterialTheme.colorScheme.onSecondaryContainer
    val colorTertiary = MaterialTheme.colorScheme.onTertiaryContainer
    val surface = MaterialTheme.colorScheme.onSurfaceVariant

    Card(
        shape = MaterialTheme.shapes.large,
        modifier = modifier
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // y axis
            drawLine(
                color = surface,
                start = Offset(0f, 0f),
                end = Offset(0f, size.height + 10),
                cap = StrokeCap.Round,
                strokeWidth = 1f,
                alpha = 0.8f
            )
            // central axis line
            drawLine(
                color = surface, start = Offset(-10f, size.center.y),
                end = Offset(size.width + 10f, size.center.y),
                cap = StrokeCap.Round,
                strokeWidth = 1f,
                alpha = 0.8f
            )
            // draw the zero text
            drawText(
                textMeasurer = textMeasurer,
                text = buildAnnotatedString { append("0") },
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium),
                topLeft = Offset(-20f, size.center.y)
            )

            val width = size.width / sensorValues.size
            sensorValues.forEachIndexed { idx, info ->
                when (info) {
                    is AxisInformation.XAxisInformation -> {
                        val graphHeight =
                            info.x * size.center.y / sensorValues.maxOf { (it as AxisInformation.XAxisInformation).x }
                        val next = sensorValues.getOrNull(idx + 1)
                        if (next != null && next is AxisInformation.XAxisInformation) {
                            val graphHeightNext =
                                next.x * size.center.y / sensorValues.maxOf { (it as AxisInformation.XAxisInformation).x }
                            drawLine(
                                color = colorPrimary,
                                start = Offset(width * (idx - 1), size.center.y - graphHeight),
                                end = Offset(width * idx, size.center.y - graphHeightNext),
                                strokeWidth = 2f,
                                cap = StrokeCap.Butt,
                                alpha = idx.toFloat() / sensorValues.size
                            )
                        }
                    }

                    is AxisInformation.XYZAxisInformation -> {
                        val graphHeightX =
                            info.x * size.center.y / sensorValues.maxOf { (it as AxisInformation.XYZAxisInformation).x }
                        val nextX = sensorValues.getOrNull(idx + 1)
                        if (nextX != null && nextX is AxisInformation.XYZAxisInformation) {
                            val graphHeightNextX =
                                nextX.x * size.center.y / sensorValues.maxOf { (it as AxisInformation.XYZAxisInformation).x }
                            drawLine(
                                color = colorPrimary,
                                start = Offset(width * (idx - 1), size.center.y - graphHeightX),
                                end = Offset(width * idx, size.center.y - graphHeightNextX),
                                strokeWidth = 2f,
                                cap = StrokeCap.Butt,
                                alpha = idx.toFloat() / sensorValues.size
                            )
                        }
                        val graphHeightY =
                            info.y * size.center.y / sensorValues.maxOf { (it as AxisInformation.XYZAxisInformation).y }
                        val nextY = sensorValues.getOrNull(idx + 1)
                        if (nextY != null && nextY is AxisInformation.XYZAxisInformation) {
                            val graphHeightNextY =
                                nextY.y * size.center.y / sensorValues.maxOf { (it as AxisInformation.XYZAxisInformation).y }
                            drawLine(
                                color = colorSecondary,
                                start = Offset(width * (idx - 1), size.center.y - graphHeightY),
                                end = Offset(width * idx, size.center.y - graphHeightNextY),
                                strokeWidth = 2f,
                                cap = StrokeCap.Butt,
                                alpha = idx.toFloat() / sensorValues.size
                            )
                        }
                        val graphHeightZ =
                            info.z * size.center.y / sensorValues.maxOf { (it as AxisInformation.XYZAxisInformation).z }
                        val nextZ = sensorValues.getOrNull(idx + 1)
                        if (nextZ != null && nextZ is AxisInformation.XYZAxisInformation) {
                            val graphHeightNextZ =
                                nextZ.z * size.center.y / sensorValues.maxOf { (it as AxisInformation.XYZAxisInformation).z }
                            drawLine(
                                color = colorTertiary,
                                start = Offset(width * (idx - 1), size.center.y - graphHeightZ),
                                end = Offset(width * idx, size.center.y - graphHeightNextZ),
                                strokeWidth = 2f,
                                cap = StrokeCap.Butt,
                                alpha = idx.toFloat() / sensorValues.size
                            )
                        }
                    }

                    else -> drawRect(Color.Blue)
                }
            }
        }
    }
}


private class SensorGraphParameters : PreviewParameterProvider<List<AxisInformation>> {
    override val values: Sequence<List<AxisInformation>>
        get() = sequenceOf(
            listOf(
                AxisInformation.XAxisInformation(x = 1.0f),
                AxisInformation.XAxisInformation(x = 2.0f),
                AxisInformation.XAxisInformation(x = 4.0f),
                AxisInformation.XAxisInformation(x = 2.0f),
                AxisInformation.XAxisInformation(x = 6.0f),
                AxisInformation.XAxisInformation(x = 11.0f),
                AxisInformation.XAxisInformation(x = 2.0f),
                AxisInformation.XAxisInformation(x = 10.0f),
                AxisInformation.XAxisInformation(x = 2.0f),
                AxisInformation.XAxisInformation(x = 1.0f)
            )
        )
}

@Preview
@Composable
fun SensorGraphPreview(
    @PreviewParameter(SensorGraphParameters::class) sensorValues: List<AxisInformation>
) {
    SensorGraph(
        sensorValues = sensorValues, modifier = Modifier.size(100.dp, 200.dp)
    )
}