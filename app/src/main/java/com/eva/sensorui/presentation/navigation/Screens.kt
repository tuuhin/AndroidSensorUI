package com.eva.sensorui.presentation.navigation

sealed class Screens(val location: String) {
    object Home : Screens("/")
    object Detailed : Screens("/detailed/")
}

object NavArgs {
    const val SENSOR_ID_PARAM = "{sensor_id}"
    const val SENSOR_ID = "sensor_id"
}