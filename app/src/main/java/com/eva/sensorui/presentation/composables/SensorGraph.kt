package com.eva.sensorui.presentation.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
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
        shape = MaterialTheme.shapes.extraSmall,
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val width = size.width / sensorValues.size
            val height = size.height / sensorValues.size
            // y axis
            drawLine(
                color = surface,
                start = Offset(0f, 0f),
                end = Offset(0f, size.height + 10),
                cap = StrokeCap.Round,
                strokeWidth = 1f,
                alpha = 0.8f
            )
            // x axis line
            drawLine(
                color = surface,
                start = Offset(-10f, size.height),
                end = Offset(size.width + 10f, size.height),
                cap = StrokeCap.Round,
                strokeWidth = 1f,
                alpha = 0.8f
            )

            // draw zero
            drawText(
                textMeasurer = textMeasurer,
                text = buildAnnotatedString { append("0") },
                style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium),
                topLeft = Offset(-20f, size.height - 12.sp.value)
            )


            sensorValues.forEachIndexed { idx, info ->
                when (info) {
                    is AxisInformation.XAxisInformation -> {
                        val max = sensorValues.maxOf {
                            if (it is AxisInformation.XAxisInformation) it.x
                            else 1f
                        }
                        val graphHeight =
                            info.x * size.height / max
                        val next = sensorValues.getOrNull(idx + 1)
                        if (next != null && next is AxisInformation.XAxisInformation) {
                            val graphHeightNext =
                                next.x * size.height / max
                            drawLine(
                                color = colorPrimary,
                                start = Offset(width * (idx - 1), size.height - graphHeight),
                                end = Offset(width * idx, size.height - graphHeightNext),
                                strokeWidth = 2f,
                                cap = StrokeCap.Butt,
                                alpha = idx.toFloat() / sensorValues.size
                            )
                        }
                    }

                    is AxisInformation.XYAxisInformation -> {
                        val maxX = sensorValues.maxOf {
                            if (it is AxisInformation.XYZAxisInformation) it.x
                            else 1f
                        }
                        val graphHeightX = info.x * size.height / maxX
                        val nextX = sensorValues.getOrNull(idx + 1)
                        if (nextX != null && nextX is AxisInformation.XYZAxisInformation) {
                            val graphHeightNextX = nextX.x * size.height / maxX
                            drawLine(
                                color = colorPrimary,
                                start = Offset(width * (idx - 1), size.height - graphHeightX),
                                end = Offset(width * idx, size.height - graphHeightNextX),
                                strokeWidth = 2f,
                                cap = StrokeCap.Butt,
                                alpha = idx.toFloat() / sensorValues.size
                            )
                        }
                        val maxY = sensorValues.maxOf {
                            if (it is AxisInformation.XYZAxisInformation) it.y
                            else 1f
                        }
                        val graphHeightY = info.y * size.height / maxY
                        val nextY = sensorValues.getOrNull(idx + 1)
                        if (nextY != null && nextY is AxisInformation.XYZAxisInformation) {
                            val graphHeightNextY = nextY.y * size.height / maxY
                            drawLine(
                                color = colorSecondary,
                                start = Offset(width * (idx - 1), size.height - graphHeightY),
                                end = Offset(width * idx, size.height - graphHeightNextY),
                                strokeWidth = 2f,
                                cap = StrokeCap.Butt,
                                alpha = idx.toFloat() / sensorValues.size
                            )
                        }
                    }

                    is AxisInformation.XYZAxisInformation -> {
                        val maxX = sensorValues.maxOf {
                            if (it is AxisInformation.XYZAxisInformation) it.x
                            else 1f
                        }
                        val graphHeightX = info.x * size.height / maxX
                        val nextX = sensorValues.getOrNull(idx + 1)
                        if (nextX != null && nextX is AxisInformation.XYZAxisInformation) {
                            val graphHeightNextX = nextX.x * size.height / maxX
                            drawLine(
                                color = colorPrimary,
                                start = Offset(width * (idx - 1), size.height - graphHeightX),
                                end = Offset(width * idx, size.height - graphHeightNextX),
                                strokeWidth = 2f,
                                cap = StrokeCap.Butt,
                                alpha = idx.toFloat() / sensorValues.size
                            )
                        }
                        val maxY = sensorValues.maxOf {
                            if (it is AxisInformation.XYZAxisInformation) it.y
                            else 1f
                        }
                        val graphHeightY = info.y * size.height / maxY
                        val nextY = sensorValues.getOrNull(idx + 1)
                        if (nextY != null && nextY is AxisInformation.XYZAxisInformation) {
                            val graphHeightNextY = nextY.y * size.height / maxY
                            drawLine(
                                color = colorSecondary,
                                start = Offset(width * (idx - 1), size.height - graphHeightY),
                                end = Offset(width * idx, size.height - graphHeightNextY),
                                strokeWidth = 2f,
                                cap = StrokeCap.Butt,
                                alpha = idx.toFloat() / sensorValues.size
                            )
                        }
                        val maxZ = sensorValues.maxOf {
                            if (it is AxisInformation.XYZAxisInformation) it.z
                            else 1f
                        }
                        val graphHeightZ = info.z * size.height / maxZ
                        val nextZ = sensorValues.getOrNull(idx + 1)
                        if (nextZ != null && nextZ is AxisInformation.XYZAxisInformation) {
                            val graphHeightNextZ = nextZ.z * size.height / maxZ
                            drawLine(
                                color = colorTertiary,
                                start = Offset(width * (idx - 1), size.height - graphHeightZ),
                                end = Offset(width * idx, size.height - graphHeightNextZ),
                                strokeWidth = 2f,
                                cap = StrokeCap.Butt,
                                alpha = idx.toFloat() / sensorValues.size
                            )
                        }
                    }
                    else -> {}
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