package com.eva.sensorui.data.repository

import android.content.Context
import android.util.Log
import com.eva.sensorui.domain.respository.SensorDataRepository
import com.eva.sensorui.data.sensors.AndroidBaseSensor
import com.eva.sensorui.utils.AxisInformation
import com.eva.sensorui.utils.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class SensorDataRepoImpl(
    private val context: Context,
) : SensorDataRepository {

    override fun getSensorData(sensor: Int): Resource<Flow<AxisInformation>> {
        try {
            val currentSensor =
                AndroidBaseSensor.getSpecificSensor(context = context, sensorType = sensor)

            val flow = callbackFlow {
                currentSensor.startListening()
                currentSensor.onSensorEvents { data ->
                    val info = when (data.size) {
                        1 -> AxisInformation.XAxisInformation(x = data[0])
                        2 -> AxisInformation.XYAxisInformation(x = data[0], y = data[1])
                        3 -> AxisInformation.XYZAxisInformation(
                            x = data[0],
                            y = data[1],
                            z = data[2]
                        )

                        else -> {
                            Log.d("TAG", data.toString())
                            AxisInformation.UnknownInformation
                        }
                    }
                    send(info)
                }
                awaitClose {
                    currentSensor.stopListening()
                }
            }
            return Resource.Success(data = flow)

        } catch (e: Exception) {
            return Resource.Error(message = e.message ?: "Cannot access the sensor information")
        }

    }
}