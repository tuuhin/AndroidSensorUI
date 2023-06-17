package com.eva.sensorui.di

import com.eva.sensorui.MainViewModel
import com.eva.sensorui.sensors.AccelerometerSensor
import com.eva.sensorui.sensors.AndroidBaseSensor
import com.eva.sensorui.sensors.CompassSensor
import com.eva.sensorui.sensors.GyroscopeSensor
import com.eva.sensorui.sensors.LightSensor
import com.eva.sensorui.sensors.ProximitySensor
import com.eva.sensorui.utils.SensorTypes
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single<AndroidBaseSensor>(named(SensorTypes.LIGHT)) { LightSensor(get()) }

    single<AndroidBaseSensor>(named(SensorTypes.PROXIMITY)) { ProximitySensor(get()) }

    single<AndroidBaseSensor>(named(SensorTypes.ACCELERATION)) { AccelerometerSensor(get()) }

    single<AndroidBaseSensor>(named(SensorTypes.COMPASS)) { CompassSensor(get()) }

    single<AndroidBaseSensor>(named(SensorTypes.GYROSCOPE)) { GyroscopeSensor(get()) }

    viewModel { MainViewModel(get(named(SensorTypes.GYROSCOPE))) }
}