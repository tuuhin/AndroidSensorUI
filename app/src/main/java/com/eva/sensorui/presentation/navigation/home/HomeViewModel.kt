package com.eva.sensorui.presentation.navigation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eva.sensorui.domain.models.BaseSensorInfoModel
import com.eva.sensorui.sensors.DeviceAvailableSensors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    sensors: DeviceAvailableSensors
) : ViewModel() {

    private val _availableSensors = MutableStateFlow(emptyList<BaseSensorInfoModel>())
    val availableSensors = _availableSensors.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            sensors.sensorList?.let { listOfSensors ->
                _availableSensors.update { listOfSensors }
            }
        }
    }
}