package com.eva.sensorui.di

import com.eva.sensorui.data.repository.SensorDataRepoImpl
import com.eva.sensorui.domain.respository.SensorDataRepository
import com.eva.sensorui.presentation.navigation.details.DetailedRouteViewModel
import com.eva.sensorui.presentation.navigation.home.HomeViewModel
import com.eva.sensorui.data.sensors.DeviceAvailableSensors
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { DeviceAvailableSensors(get()) }

    viewModel { HomeViewModel(get()) }

    single<SensorDataRepository> { SensorDataRepoImpl(get()) }

    viewModel { DetailedRouteViewModel(get(), get(), get()) }

}