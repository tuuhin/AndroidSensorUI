package com.eva.sensorui.domain.models


data class BaseSensorInfoModel(
    val imageRes: Int,
    val sensorType: Int,
    val name: String,
    val vendor: String,
    val range: Float,
    val power: Float,
)
