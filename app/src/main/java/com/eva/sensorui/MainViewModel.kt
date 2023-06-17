package com.eva.sensorui

import androidx.lifecycle.ViewModel
import com.eva.sensorui.sensors.AndroidBaseSensor
import com.eva.sensorui.utils.AxisInformation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel(
    private val sensor: AndroidBaseSensor
) : ViewModel() {

    private val _sensorOutput =
        MutableStateFlow<AxisInformation>(AxisInformation.LoadingInformation)
    val sensorData = _sensorOutput.asStateFlow()

    init {
        sensor.startListening()
        sensor.onSensorEvents { events ->
            when (events.size) {
                1 -> _sensorOutput.update { AxisInformation.XAxisInformation(x = events[0]) }
                2 -> _sensorOutput.update {
                    AxisInformation.XYAxisInformation(x = events[0], y = events[1])
                }

                3 -> _sensorOutput.update {
                    AxisInformation.XYZAxisInformation(
                        x = events[0],
                        y = events[1],
                        z = events[2]
                    )
                }

                else -> _sensorOutput.update { AxisInformation.UnknownInformation }
            }
        }
    }

    override fun onCleared() {
        sensor.stopListening()
        super.onCleared()
    }
}