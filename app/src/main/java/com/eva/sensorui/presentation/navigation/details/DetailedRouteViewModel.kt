package com.eva.sensorui.presentation.navigation.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eva.sensorui.domain.models.BaseSensorInfoModel
import com.eva.sensorui.domain.respository.SensorDataRepository
import com.eva.sensorui.presentation.navigation.NavArgs
import com.eva.sensorui.data.sensors.DeviceAvailableSensors
import com.eva.sensorui.domain.models.GraphData
import com.eva.sensorui.utils.AxisInformation
import com.eva.sensorui.utils.GraphAxisUpdater
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
    private val repository: SensorDataRepository,
    savedStateHandle: SavedStateHandle,
    private val sensors: DeviceAvailableSensors
) : ViewModel() {

    private val _queue = GraphAxisUpdater(20)

    private val _sensorInfo = MutableStateFlow<BaseSensorInfoModel?>(null)
    val sensorInformation = _sensorInfo.asStateFlow()

    private val _currentSensorValue =
        MutableStateFlow<AxisInformation>(AxisInformation.LoadingInformation)
    val currentSensorValue = _currentSensorValue.asStateFlow()

    val sensorGraphData = _queue.graphData.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(400L),
        GraphData()
    )

    init {
        val sensorType = savedStateHandle.get<Int>(NavArgs.SENSOR_ID) ?: -1
        if (sensorType != -1) {
            _sensorInfo.update { sensors.getSensorInfoFromType(sensorType) }

            viewModelScope.launch(Dispatchers.IO) {
                when (val data = repository.getSensorData(sensor = sensorType)) {
                    is Resource.Error -> {
                        Log.d("ERROR", "Error Occurred")
                    }

                    is Resource.Success -> {
                        data.data
                            ?.onEach { info ->
                                _currentSensorValue.update { info }
                                _queue.add(info)
                            }?.launchIn(viewModelScope)
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