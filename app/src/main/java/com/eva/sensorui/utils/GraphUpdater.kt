package com.eva.sensorui.utils

import com.eva.sensorui.domain.models.GraphData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

class GraphAxisUpdater(
    capacity: Int
) : FixedLengthQueue<AxisInformation>(capacity) {

    init {
        super.prefill(AxisInformation.LoadingInformation)
    }

    private val _maxAxisInfo = MutableStateFlow(1f)
    private val _minAxisInfo = MutableStateFlow(0f)

    val graphData =
        combine(_maxAxisInfo, _minAxisInfo) { max, min ->
            GraphData(max = max, min = min, indices = super.values)
        }

    override fun add(item: AxisInformation) {
        super.add(item)
        when (item) {
            AxisInformation.LoadingInformation -> {
                _minAxisInfo.update { 1f }
                _maxAxisInfo.update { 0f }
            }

            AxisInformation.UnknownInformation -> {}
            is AxisInformation.XAxisInformation -> {
                if (item.x > _maxAxisInfo.value)
                    _maxAxisInfo.update { item.x }
                if (item.x < _minAxisInfo.value)
                    _minAxisInfo.update { item.x }
            }

            is AxisInformation.XYAxisInformation -> {
                val max = maxOf(item.x, item.y)
                val min = minOf(item.x, item.y)
                if (max > _maxAxisInfo.value)
                    _maxAxisInfo.update { max }
                if (min < _minAxisInfo.value)
                    _minAxisInfo.update { min }
            }

            is AxisInformation.XYZAxisInformation -> {
                val max = maxOf(item.x, item.y, item.z)
                val min = minOf(item.x, item.y, item.z)
                if (max > _maxAxisInfo.value)
                    _maxAxisInfo.update { max }
                if (min < _minAxisInfo.value)
                    _minAxisInfo.update { min }
            }
        }
    }

}