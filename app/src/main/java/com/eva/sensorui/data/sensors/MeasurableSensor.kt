package com.eva.sensorui.data.sensors

abstract class MeasurableSensor(
    protected val sensorType: Int
) {
    protected var onSensorValueChanged: (suspend (List<Float>) -> Unit)? = null

    abstract val sensorAvailable: Boolean

    abstract fun startListening()

    abstract fun stopListening()

    fun onSensorEvents(listener: (suspend (List<Float>) -> Unit)?) {
        onSensorValueChanged = listener
    }
}