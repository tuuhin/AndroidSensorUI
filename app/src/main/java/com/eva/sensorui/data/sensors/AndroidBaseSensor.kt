package com.eva.sensorui.data.sensors

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.core.content.getSystemService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

abstract class AndroidBaseSensor(
    private val context: Context,
    private val feature: String,
    sensorType: Int
) : MeasurableSensor(sensorType), SensorEventListener {

    //  private val tag = "ANDROID_BASE_SENSOR"

    private val sensorManager by lazy { context.getSystemService<SensorManager>() }

    private var currentSensor: Sensor? = null

    override val sensorAvailable
        get() = context.packageManager.hasSystemFeature(feature)

    private lateinit var _scope: CoroutineScope

    override fun startListening() {
        if (!sensorAvailable)
            return

        if (sensorManager != null && currentSensor == null) {
            currentSensor = sensorManager?.getDefaultSensor(sensorType)
        }
        _scope = CoroutineScope(Dispatchers.Default)
        currentSensor?.let { sensor ->
            sensorManager?.registerListener(
                this,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    override fun stopListening() {
        if (!sensorAvailable) return
        _scope.cancel()
        sensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (!sensorAvailable && ::_scope.isInitialized) return
        if (event?.sensor?.type == sensorType) {
            // Log.d(tag, event.values.toList().toString())
            _scope.launch {
                if (isActive)
                    onSensorValueChanged?.invoke(event.values.toList())
            }

        }
    }

    override fun onAccuracyChanged(event: Sensor?, accuracy: Int) = Unit

    companion object {
        fun getSpecificSensor(context: Context, sensorType: Int): AndroidBaseSensor =
            object : AndroidBaseSensor(
                context = context,
                feature = when (sensorType) {
                    Sensor.TYPE_ACCELEROMETER -> PackageManager.FEATURE_SENSOR_ACCELEROMETER
                    Sensor.TYPE_ACCELEROMETER_LIMITED_AXES -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                        PackageManager.FEATURE_SENSOR_ACCELEROMETER_LIMITED_AXES
                    else PackageManager.FEATURE_SENSOR_ACCELEROMETER

                    Sensor.TYPE_ACCELEROMETER_UNCALIBRATED -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                        PackageManager.FEATURE_SENSOR_ACCELEROMETER_LIMITED_AXES_UNCALIBRATED
                    else
                        PackageManager.FEATURE_SENSOR_ACCELEROMETER

                    Sensor.TYPE_AMBIENT_TEMPERATURE -> PackageManager.FEATURE_SENSOR_AMBIENT_TEMPERATURE
                    Sensor.TYPE_PRESSURE -> PackageManager.FEATURE_SENSOR_BAROMETER
                    Sensor.TYPE_MAGNETIC_FIELD -> PackageManager.FEATURE_SENSOR_COMPASS
                    Sensor.TYPE_HEAD_TRACKER -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                        PackageManager.FEATURE_SENSOR_DYNAMIC_HEAD_TRACKER
                    else throw Exception("Feature Unavailable")

                    Sensor.TYPE_GYROSCOPE -> PackageManager.FEATURE_SENSOR_GYROSCOPE
                    Sensor.TYPE_GYROSCOPE_LIMITED_AXES -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                        PackageManager.FEATURE_SENSOR_GYROSCOPE_LIMITED_AXES
                    else
                        PackageManager.FEATURE_SENSOR_GYROSCOPE

                    Sensor.TYPE_GYROSCOPE_LIMITED_AXES_UNCALIBRATED -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                        PackageManager.FEATURE_SENSOR_GYROSCOPE_LIMITED_AXES_UNCALIBRATED
                    else
                        PackageManager.FEATURE_SENSOR_GYROSCOPE

                    Sensor.TYPE_HEADING -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                        PackageManager.FEATURE_SENSOR_HEADING
                    else throw Exception("Feature Unavailable")

                    Sensor.TYPE_HEART_RATE -> PackageManager.FEATURE_SENSOR_HEART_RATE
                    Sensor.TYPE_HINGE_ANGLE -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                        PackageManager.FEATURE_SENSOR_HINGE_ANGLE
                    else throw Exception("Feature unavailable")

                    Sensor.TYPE_LIGHT -> PackageManager.FEATURE_SENSOR_LIGHT
                    Sensor.TYPE_PROXIMITY -> PackageManager.FEATURE_SENSOR_PROXIMITY
                    Sensor.TYPE_STEP_COUNTER -> PackageManager.FEATURE_SENSOR_STEP_COUNTER
                    Sensor.TYPE_STEP_DETECTOR -> PackageManager.FEATURE_SENSOR_STEP_DETECTOR
                    else -> PackageManager.FEATURE_SENSOR_LIGHT

                }, sensorType = sensorType
            ) {}

    }
}