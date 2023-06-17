package com.eva.sensorui.sensors

abstract class MeasurableSensor(
    protected val sensorType: Int
) {
    protected var onSensorValueChanged: ((List<Float>) -> Unit)? = null

    abstract val sensorAvailable: Boolean

    abstract fun startListening()

    abstract fun stopListening()

    fun onSensorEvents(listener: ((List<Float>) -> Unit)?) {
        onSensorValueChanged = listener
    }
}