package com.eva.sensorui.presentation.navigation.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eva.sensorui.domain.models.BaseSensorInfoModel
import com.eva.sensorui.domain.respository.SensorDataRepository
import com.eva.sensorui.presentation.navigation.NavArgs
import com.eva.sensorui.data.sensors.DeviceAvailableSensors
import com.eva.sensorui.utils.AxisInformation
import com.eva.sensorui.utils.FixedLengthQueue
import com.eva.sensorui.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailedRouteViewModel(
    repository: SensorDataRepository,
    savedStateHandle: SavedStateHandle,
    sensors: DeviceAvailableSensors
) : ViewModel() {

    private val sensorType = savedStateHandle.get<Int>(NavArgs.SENSOR_ID) ?: -1

    private val _queue = FixedLengthQueue<AxisInformation>(20)
        .apply {
            prefill(AxisInformation.LoadingInformation)
        }

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

            viewModelScope.launch(Dispatchers.IO) {
                when (val data = repository.getSensorData(sensor = sensorType)) {
                    is Resource.Error -> {
                        Log.d("ERROR", "Error Occured")
                    }

                    is Resource.Success -> {
                        data.data?.onEach { info ->
                            _currentSensorValue.update { info }
                            _queue.add(info)
                        }
                            ?.launchIn(viewModelScope)
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        _queue.clear()
    }
}