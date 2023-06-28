package com.eva.sensorui.presentation.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eva.sensorui.utils.AxisInformation
import java.text.DecimalFormat
import kotlin.math.abs

@OptIn(ExperimentalTextApi::class)
@Composable
fun SensorGraph(
    maximumRange: Float,
    minimumRange: Float,
    sensorValues: List<AxisInformation>,
    modifier: Modifier = Modifier,
    strokeCap: StrokeCap = StrokeCap.Butt,
    strokeWidth: Float = 2f,
    textStyle: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.End
    ),
    colorPrimary: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    colorSecondary: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    colorTertiary: Color = MaterialTheme.colorScheme.onTertiaryContainer,
    surface: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    onSurface: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    spanStyle: SpanStyle = SpanStyle(color = MaterialTheme.colorScheme.onSurfaceVariant)
) {

    val textMeasurer = rememberTextMeasurer()

    val formatter = remember { DecimalFormat("##.#") }

    val minimumAbs = remember(minimumRange) { abs(minimumRange) }

    val maximumAbs = remember(maximumRange) { abs(maximumRange) }

    Card(
        shape = MaterialTheme.shapes.extraSmall,
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Canvas(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize()
        ) {
            val width = size.width / sensorValues.size
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
            // x-max line
            drawLine(
                color = surface,
                start = Offset(-10f, 0f),
                end = Offset(size.width + 10f, 0f),
                cap = StrokeCap.Round,
                strokeWidth = 1f,
                alpha = 0.8f
            )

            if (minimumRange <= 0f) {
                val yAxis = (maximumAbs / (maximumAbs + minimumAbs)) * size.height
                drawLine(
                    color = surface,
                    start = Offset(-10f, yAxis),
                    end = Offset(size.width + 10f, yAxis),
                    cap = StrokeCap.Round,
                    strokeWidth = 1f,
                    alpha = 0.8f
                )
            }

            sensorValues.forEachIndexed { idx, info ->
                when (info) {
                    is AxisInformation.XAxisInformation -> {
                        val graphHeight =
                            (info.x + minimumAbs) * size.height / (maximumAbs + minimumAbs)
                        val next = sensorValues.getOrNull(idx + 1)
                        if (next != null && next is AxisInformation.XAxisInformation) {
                            val graphHeightNext =
                                (next.x + minimumAbs) * size.height / (maximumAbs + minimumAbs)
                            drawLine(
                                color = colorPrimary,
                                start = Offset(width * (idx), size.height - graphHeight),
                                end = Offset(width * (idx + 1), size.height - graphHeightNext),
                                strokeWidth = strokeWidth,
                                cap = strokeCap,
                                alpha = (idx + 1).toFloat() / sensorValues.size
                            )
                        }
                    }

                    is AxisInformation.XYAxisInformation -> {

                        val graphHeightX =
                            (info.x + minimumAbs) * size.height / (maximumAbs + minimumAbs)
                        val nextX = sensorValues.getOrNull(idx + 1)
                        if (nextX != null && nextX is AxisInformation.XYZAxisInformation) {
                            val graphHeightNextX =
                                (nextX.x + minimumAbs) * size.height / (maximumAbs + minimumAbs)
                            drawLine(
                                color = colorPrimary,
                                start = Offset(width * (idx - 1), size.height - graphHeightX),
                                end = Offset(width * idx, size.height - graphHeightNextX),
                                strokeWidth = strokeWidth,
                                cap = strokeCap,
                                alpha = idx.toFloat() / sensorValues.size
                            )
                        }

                        val graphHeightY =
                            (info.y + minimumAbs) * size.height / (maximumAbs + minimumAbs)
                        val nextY = sensorValues.getOrNull(idx + 1)
                        if (nextY != null && nextY is AxisInformation.XYZAxisInformation) {
                            val graphHeightNextY =
                                (nextY.y + minimumAbs) * size.height / (maximumAbs + minimumAbs)
                            drawLine(
                                color = colorSecondary,
                                start = Offset(width * (idx - 1), size.height - graphHeightY),
                                end = Offset(width * idx, size.height - graphHeightNextY),
                                strokeWidth = strokeWidth,
                                cap = strokeCap,
                                alpha = idx.toFloat() / sensorValues.size
                            )
                        }
                    }

                    is AxisInformation.XYZAxisInformation -> {

                        val graphHeightX =
                            (info.x + minimumAbs) * size.height / (maximumAbs + minimumAbs)
                        val nextX = sensorValues.getOrNull(idx + 1)
                        if (nextX != null && nextX is AxisInformation.XYZAxisInformation) {
                            val graphHeightNextX =
                                (nextX.x + minimumAbs) * size.height / (maximumAbs + minimumAbs)
                            drawLine(
                                color = colorPrimary,
                                start = Offset(width * (idx - 1), size.height - graphHeightX),
                                end = Offset(width * idx, size.height - graphHeightNextX),
                                strokeWidth = strokeWidth,
                                cap = strokeCap,
                                alpha = idx.toFloat() / sensorValues.size
                            )
                        }

                        val graphHeightY =
                            (info.y + minimumAbs) * size.height / (maximumAbs + minimumAbs)
                        val nextY = sensorValues.getOrNull(idx + 1)
                        if (nextY != null && nextY is AxisInformation.XYZAxisInformation) {
                            val graphHeightNextY =
                                (nextY.y + minimumAbs) * size.height / (maximumAbs + minimumAbs)
                            drawLine(
                                color = colorSecondary,
                                start = Offset(width * (idx - 1), size.height - graphHeightY),
                                end = Offset(width * idx, size.height - graphHeightNextY),
                                strokeWidth = strokeWidth,
                                cap = strokeCap,
                                alpha = idx.toFloat() / sensorValues.size
                            )
                        }

                        val graphHeightZ =
                            (info.z + minimumAbs) * size.height / (maximumAbs + minimumAbs)
                        val nextZ = sensorValues.getOrNull(idx + 1)
                        if (nextZ != null && nextZ is AxisInformation.XYZAxisInformation) {
                            val graphHeightNextZ =
                                (nextZ.z + minimumAbs) * size.height / (maximumAbs + minimumAbs)
                            drawLine(
                                color = colorTertiary,
                                start = Offset(width * (idx - 1), size.height - graphHeightZ),
                                end = Offset(width * idx, size.height - graphHeightNextZ),
                                strokeWidth = strokeWidth,
                                cap = strokeCap,
                                alpha = idx.toFloat() / sensorValues.size
                            )
                        }
                    }

                    AxisInformation.UnknownInformation -> {
                        drawText(
                            textMeasurer = textMeasurer,
                            text = buildAnnotatedString {
                                withStyle(SpanStyle(color = onSurface)) {
                                    append("Data Not Found")
                                }
                            },
                            style = textStyle.copy(textAlign = TextAlign.Center),
                            topLeft = Offset(center.x, center.y)
                        )
                    }

                    else -> {}
                }
            }

            // draw max
            drawText(
                textMeasurer = textMeasurer,
                text = buildAnnotatedString {
                    withStyle(spanStyle) {
                        append(formatter.format(maximumRange))
                    }
                },
                style = textStyle,
                topLeft = Offset(-32.sp.value, -16.sp.value)
            )

            if (minimumRange < -1f) {
                val yAxis = (maximumAbs / (maximumAbs + minimumAbs)) * size.height
                drawText(
                    textMeasurer = textMeasurer,
                    text = buildAnnotatedString {
                        withStyle(spanStyle) {
                            append(formatter.format(0))
                        }
                    },
                    style = textStyle,
                    topLeft = Offset(-32.sp.value, yAxis - 32.sp.value)
                )
            }
            // draw min
            drawText(
                textMeasurer = textMeasurer,
                text = buildAnnotatedString {
                    withStyle(spanStyle) {
                        append(formatter.format(minimumRange))
                    }
                },
                style = textStyle,
                topLeft = Offset(-36.sp.value, size.height - 32.sp.value)
            )

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
                AxisInformation.XAxisInformation(x = 10.0f),
                AxisInformation.XAxisInformation(x = 2.0f),
                AxisInformation.XAxisInformation(x = -10.0f),
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
        sensorValues = sensorValues,
        modifier = Modifier.size(100.dp, 200.dp),
        maximumRange = 10f,
        minimumRange = -10f,
    )
}