package com.eva.sensorui.presentation.utils

sealed interface UiEvents {
    data class ShowSnackBar(val message: String) : UiEvents
}
