package com.eva.sensorui.domain.respository

import com.eva.sensorui.utils.AxisInformation
import com.eva.sensorui.utils.Resource
import kotlinx.coroutines.flow.Flow

interface SensorDataRepository {

    fun getSensorData(sensor:Int) : Resource<Flow<AxisInformation>>
}