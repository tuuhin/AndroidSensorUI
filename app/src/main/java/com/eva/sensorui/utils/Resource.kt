package com.eva.sensorui.utils

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data = data, message = null)
    class Error<T>(message: String) : Resource<T>(data = null, message = message)
}
