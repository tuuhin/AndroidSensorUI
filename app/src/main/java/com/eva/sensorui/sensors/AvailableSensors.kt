package com.eva.sensorui.sensors

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor

class LightSensor(context: Context) : AndroidBaseSensor(
    context = context,
    feature = PackageManager.FEATURE_SENSOR_LIGHT,
    sensorType = Sensor.TYPE_LIGHT
)

class ProximitySensor(context: Context) : AndroidBaseSensor(
    context = context,
    feature = PackageManager.FEATURE_SENSOR_PROXIMITY,
    sensorType = Sensor.TYPE_PROXIMITY
)


class AccelerometerSensor(context: Context) : AndroidBaseSensor(
    context = context,
    feature = PackageManager.FEATURE_SENSOR_ACCELEROMETER,
    sensorType = Sensor.TYPE_ACCELEROMETER
)

class CompassSensor(context: Context) : AndroidBaseSensor(
    context = context,
    feature = PackageManager.FEATURE_SENSOR_COMPASS,
    sensorType = Sensor.TYPE_MAGNETIC_FIELD
)

class GyroscopeSensor(context: Context) : AndroidBaseSensor(
    context = context,
    feature = PackageManager.FEATURE_SENSOR_GYROSCOPE,
    sensorType = Sensor.TYPE_GYROSCOPE
)

class GravitySensor(context: Context) : AndroidBaseSensor(
    context = context,
    feature = PackageManager.FEATURE_SENSOR_ACCELEROMETER,
    sensorType = Sensor.TYPE_GRAVITY
)

class HumiditySensor(context: Context) : AndroidBaseSensor(
    context = context,
    feature = PackageManager.FEATURE_SENSOR_RELATIVE_HUMIDITY,
    sensorType = Sensor.TYPE_RELATIVE_HUMIDITY
)

class PressureSensor(context: Context) : AndroidBaseSensor(
    context = context,
    feature = PackageManager.FEATURE_SENSOR_BAROMETER,
    sensorType = Sensor.TYPE_PRESSURE
)

class TemperatureSensor(context: Context) : AndroidBaseSensor(
    context = context,
    feature = PackageManager.FEATURE_SENSOR_AMBIENT_TEMPERATURE,
    sensorType = Sensor.TYPE_AMBIENT_TEMPERATURE
)

class OrientationSensor(context: Context) : AndroidBaseSensor(
    context = context,
    feature = PackageManager.FEATURE_SENSOR_COMPASS,
    sensorType = Sensor.TYPE_ORIENTATION
)

