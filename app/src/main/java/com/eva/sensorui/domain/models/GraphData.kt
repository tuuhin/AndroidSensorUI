package com.eva.sensorui.domain.models

import com.eva.sensorui.utils.AxisInformation

data class GraphData(
    val max: Float = 1f,
    val min: Float = 0f,
    val indices: List<AxisInformation> = emptyList()
)
