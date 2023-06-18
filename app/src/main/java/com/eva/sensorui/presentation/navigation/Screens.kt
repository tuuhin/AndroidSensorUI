package com.eva.sensorui.presentation.navigation

sealed class Screens(val location: String) {
    object Home : Screens("/")
    object Detailed : Screens("/detailed")
}
