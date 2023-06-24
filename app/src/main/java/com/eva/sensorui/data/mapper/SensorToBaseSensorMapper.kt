package com.eva.sensorui.data.mapper

import android.hardware.Sensor
import com.eva.sensorui.R
import com.eva.sensorui.domain.models.BaseSensorInfoModel

fun Sensor.toBaseSensor(): BaseSensorInfoModel = BaseSensorInfoModel(
    name = name,
    sensorType = type,
    vendor = vendor,
    power = power,
    range = maximumRange,
    imageRes = when (type) {
        Sensor.TYPE_GYROSCOPE -> R.drawable.ic_sensor_gyroscope
        Sensor.TYPE_LIGHT -> R.drawable.ic_sensor_brightness
        Sensor.TYPE_PROXIMITY -> R.drawable.ic_sensor_proximity
        Sensor.TYPE_GRAVITY -> R.drawable.ic_sensor_gravity
        Sensor.TYPE_ACCELEROMETER -> R.drawable.ic_sensor_linear_acceleration
        Sensor.TYPE_RELATIVE_HUMIDITY -> R.drawable.ic_sensor_humidity
        Sensor.TYPE_MAGNETIC_FIELD -> R.drawable.ic_sensor_magnet
        Sensor.TYPE_PRESSURE -> R.drawable.ic_sensor_pressure
        Sensor.TYPE_ROTATION_VECTOR -> R.drawable.ic_sensor_rotation
        Sensor.TYPE_AMBIENT_TEMPERATURE -> R.drawable.ic_sensor_temperature
        Sensor.TYPE_ORIENTATION -> R.drawable.ic_sensor_rotation
        else -> R.drawable.ic_sensor_unknown
    },
    version = version,
)
