package com.eva.sensorui.presentation.navigation.details

import android.content.Context
import android.hardware.Sensor
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eva.sensorui.domain.models.BaseSensorInfoModel
import com.eva.sensorui.presentation.navigation.NavArgs
import com.eva.sensorui.sensors.AccelerometerSensor
import com.eva.sensorui.sensors.AndroidBaseSensor
import com.eva.sensorui.sensors.CompassSensor
import com.eva.sensorui.sensors.DeviceAvailableSensors
import com.eva.sensorui.sensors.GravitySensor
import com.eva.sensorui.sensors.GyroscopeSensor
import com.eva.sensorui.sensors.HumiditySensor
import com.eva.sensorui.sensors.LightSensor
import com.eva.sensorui.sensors.OrientationSensor
import com.eva.sensorui.sensors.PressureSensor
import com.eva.sensorui.sensors.ProximitySensor
import com.eva.sensorui.sensors.TemperatureSensor
import com.eva.sensorui.utils.AxisInformation
import com.eva.sensorui.utils.FixedLengthQueue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailedRouteViewModel(
    context: Context,
    savedStateHandle: SavedStateHandle,
    sensors: DeviceAvailableSensors
) : ViewModel() {

    private val sensorType = savedStateHandle.get<Int>(NavArgs.SENSOR_ID) ?: -1

    private lateinit var _sensor: AndroidBaseSensor

    private val _queue = FixedLengthQueue<AxisInformation>(100)

    private val _sensorInfo = MutableStateFlow<BaseSensorInfoModel?>(null)
    val sensorFlow = _sensorInfo.asStateFlow()

    private val _currentSensorValue =
        MutableStateFlow<AxisInformation>(AxisInformation.LoadingInformation)
    val currentSensorValue = _currentSensorValue.asStateFlow()

    val sensorValues = _queue.asFlow().stateIn(
        viewModelScope, SharingStarted.Lazily,
        emptyList()
    )

    init {
        if (sensorType != -1) {
            _sensorInfo.update { sensors.getSensorInfoFromType(sensorType) }

            _sensor = when (sensorType) {
                Sensor.TYPE_GYROSCOPE -> GyroscopeSensor(context)
                Sensor.TYPE_LIGHT -> LightSensor(context)
                Sensor.TYPE_PROXIMITY -> ProximitySensor(context)
                Sensor.TYPE_GRAVITY -> GravitySensor(context)
                Sensor.TYPE_ACCELEROMETER -> AccelerometerSensor(context)
                Sensor.TYPE_RELATIVE_HUMIDITY -> HumiditySensor(context)
                Sensor.TYPE_MAGNETIC_FIELD -> CompassSensor(context)
                Sensor.TYPE_PRESSURE -> PressureSensor(context)
                Sensor.TYPE_AMBIENT_TEMPERATURE -> TemperatureSensor(context)
                Sensor.TYPE_ORIENTATION -> OrientationSensor(context)
                else -> throw Exception("Sensor not found")
            }
            _sensor.startListening()

            viewModelScope.launch(Dispatchers.Default) {
                _sensor.onSensorEvents { data ->
                    val info = when (data.size) {
                        1 -> AxisInformation.XAxisInformation(x = data[0])
                        2 -> AxisInformation.XYAxisInformation(x = data[0], y = data[1])
                        3 -> AxisInformation.XYZAxisInformation(
                            x = data[0],
                            y = data[1],
                            z = data[2]
                        )

                        else -> AxisInformation.UnknownInformation
                    }
                    _currentSensorValue.update { info }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (::_sensor.isInitialized) {
            _sensor.stopListening()
            _queue.clear()
        }
    }

}