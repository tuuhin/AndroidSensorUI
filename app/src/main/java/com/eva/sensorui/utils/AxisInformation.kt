package com.eva.sensorui.utils

sealed interface AxisInformation {
    data class XAxisInformation(val x: Float) : AxisInformation
    data class XYAxisInformation(val x: Float, val y: Float) : AxisInformation
    data class XYZAxisInformation(val x: Float, val y: Float, val z: Float) : AxisInformation
    object UnknownInformation : AxisInformation
    object LoadingInformation : AxisInformation
}