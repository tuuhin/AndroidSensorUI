package com.eva.sensorui.data.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.core.content.getSystemService
import com.eva.sensorui.R
import com.eva.sensorui.data.mapper.toBaseSensor
import com.eva.sensorui.domain.models.BaseSensorInfoModel

class DeviceAvailableSensors(context: Context) {

    private val sensorManager = context.getSystemService<SensorManager>()

    val sensorList: List<BaseSensorInfoModel>?
        get() = sensorManager?.getSensorList(Sensor.TYPE_ALL)?.map { it.toBaseSensor() }
            ?.filter { it.imageRes != R.drawable.ic_sensor_unknown }

    fun getSensorInfoFromType(type: Int): BaseSensorInfoModel? {
        return sensorManager?.getDefaultSensor(type)?.toBaseSensor()
    }
}