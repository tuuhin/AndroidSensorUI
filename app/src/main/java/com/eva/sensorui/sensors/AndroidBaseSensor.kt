package com.eva.sensorui.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.core.content.getSystemService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
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
                onSensorValueChanged?.invoke(event.values.toList())
            }

        }
    }

    override fun onAccuracyChanged(event: Sensor?, accuracy: Int) = Unit
}