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
    modifier: Modifier = Modifier
) {

    val textMeasurer = rememberTextMeasurer()

    val formatter = remember {
        DecimalFormat("##.#")
    }

    val colorPrimary = MaterialTheme.colorScheme.onPrimaryContainer
    val colorSecondary = MaterialTheme.colorScheme.onSecondaryContainer
    val colorTertiary = MaterialTheme.colorScheme.onTertiaryContainer
    val surface = MaterialTheme.colorScheme.onSurfaceVariant
    val onSurface = MaterialTheme.colorScheme.onSurfaceVariant

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
                start = Offset(-10f,0f ),
                end = Offset(size.width + 10f,0f),
                cap = StrokeCap.Round,
                strokeWidth = 1f,
                alpha = 0.8f
            )

            if (minimumRange <= 0f) {
                val yAxis =
                    (abs(maximumRange) / (abs(maximumRange) + abs(minimumRange))) * size.height

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
                            (info.x + abs(minimumRange)) * size.height / (abs(maximumRange) + abs(
                                minimumRange
                            ))
                        val next = sensorValues.getOrNull(idx + 1)
                        if (next != null && next is AxisInformation.XAxisInformation) {
                            val graphHeightNext =
                                next.x * size.height / maximumRange
                            drawLine(
                                color = colorPrimary,
                                start = Offset(width * (idx), size.height - graphHeight),
                                end = Offset(width * (idx + 1), size.height - graphHeightNext),
                                strokeWidth = 2.5f,
                                cap = StrokeCap.Butt,
                                alpha = (idx + 1).toFloat() / sensorValues.size
                            )
                        }
                    }

                    is AxisInformation.XYAxisInformation -> {

                        val graphHeightX = info.x * size.height / maximumRange
                        val nextX = sensorValues.getOrNull(idx + 1)
                        if (nextX != null && nextX is AxisInformation.XYZAxisInformation) {
                            val graphHeightNextX =
                                (nextX.x + abs(minimumRange)) * size.height / (abs(maximumRange) + abs(
                                    minimumRange
                                ))
                            drawLine(
                                color = colorPrimary,
                                start = Offset(width * (idx - 1), size.height - graphHeightX),
                                end = Offset(width * idx, size.height - graphHeightNextX),
                                strokeWidth = 2.5f,
                                cap = StrokeCap.Butt,
                                alpha = idx.toFloat() / sensorValues.size
                            )
                        }

                        val graphHeightY = info.y * size.height / maximumRange
                        val nextY = sensorValues.getOrNull(idx + 1)
                        if (nextY != null && nextY is AxisInformation.XYZAxisInformation) {
                            val graphHeightNextY = nextY.y * size.height / maximumRange
                            drawLine(
                                color = colorSecondary,
                                start = Offset(width * (idx - 1), size.height - graphHeightY),
                                end = Offset(width * idx, size.height - graphHeightNextY),
                                strokeWidth = 2.5f,
                                cap = StrokeCap.Butt,
                                alpha = idx.toFloat() / sensorValues.size
                            )
                        }
                    }

                    is AxisInformation.XYZAxisInformation -> {

                        val graphHeightX =
                            (info.x + abs(minimumRange)) * size.height / (abs(maximumRange) + abs(
                                minimumRange
                            ))
                        val nextX = sensorValues.getOrNull(idx + 1)
                        if (nextX != null && nextX is AxisInformation.XYZAxisInformation) {
                            val graphHeightNextX =
                                (nextX.x + abs(minimumRange)) * size.height / (abs(maximumRange) + abs(
                                    minimumRange
                                ))
                            drawLine(
                                color = colorPrimary,
                                start = Offset(width * (idx - 1), size.height - graphHeightX),
                                end = Offset(width * idx, size.height - graphHeightNextX),
                                strokeWidth = 2.5f,
                                cap = StrokeCap.Butt,
                                alpha = idx.toFloat() / sensorValues.size
                            )
                        }

                        val graphHeightY =
                            (info.y + abs(minimumRange)) * size.height / (abs(maximumRange) + abs(
                                minimumRange
                            ))
                        val nextY = sensorValues.getOrNull(idx + 1)
                        if (nextY != null && nextY is AxisInformation.XYZAxisInformation) {
                            val graphHeightNextY =
                                (nextY.y + abs(minimumRange)) * size.height / (abs(maximumRange) + abs(
                                    minimumRange
                                ))
                            drawLine(
                                color = colorSecondary,
                                start = Offset(width * (idx - 1), size.height - graphHeightY),
                                end = Offset(width * idx, size.height - graphHeightNextY),
                                strokeWidth = 2.5f,
                                cap = StrokeCap.Butt,
                                alpha = idx.toFloat() / sensorValues.size
                            )
                        }

                        val graphHeightZ =
                            (info.z + abs(minimumRange)) * size.height / (abs(maximumRange) + abs(
                                minimumRange
                            ))
                        val nextZ = sensorValues.getOrNull(idx + 1)
                        if (nextZ != null && nextZ is AxisInformation.XYZAxisInformation) {
                            val graphHeightNextZ =
                                (nextZ.z + abs(minimumRange)) * size.height / (abs(maximumRange) + abs(
                                    minimumRange
                                ))
                            drawLine(
                                color = colorTertiary,
                                start = Offset(width * (idx - 1), size.height - graphHeightZ),
                                end = Offset(width * idx, size.height - graphHeightNextZ),
                                strokeWidth = 2.5f,
                                cap = StrokeCap.Butt,
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
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center
                            ),
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
                    withStyle(SpanStyle(color = onSurface)) {
                        append(formatter.format(maximumRange))
                    }
                },
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.End
                ),
                topLeft = Offset(-32.sp.value, -16.sp.value)
            )

            if (minimumRange < -1f ) {
                val yAxis =
                    (abs(maximumRange) / (abs(maximumRange) + abs(minimumRange))) * size.height
                drawText(
                    textMeasurer = textMeasurer,
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(color = onSurface)) {
                            append("0")
                        }
                    },
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.End
                    ),
                    topLeft = Offset(-32.sp.value, yAxis - 32.sp.value)
                )
            }


            // draw min
            drawText(
                textMeasurer = textMeasurer,
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = onSurface)) {
                        append(formatter.format(minimumRange))
                    }
                },
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.End
                ),
                topLeft = Offset(
                    -36.sp.value, size.height - 32.sp.value
                )
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
        minimumRange = -10f
    )
}