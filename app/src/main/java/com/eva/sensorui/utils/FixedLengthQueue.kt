package com.eva.sensorui.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.LinkedList

class FixedLengthQueue<T>(private val capacity: Int) {

    private val queue = LinkedList<T>()
    private val queueFlow = MutableStateFlow(queue.toList())

    val values: List<T?>
        get() = queue

    val first: T?
        get() = queue.firstOrNull()

    val isFull: Boolean
        get() = queue.size >= capacity

    val isEmpty: Boolean
        get() = queue.isEmpty()

    fun clear() = queue.clear()

    val size: Int
        get() = queue.size

    fun add(item: T) {
        if (queue.size >= capacity)
            queue.removeFirst()
        queue.addLast(item)
        queueFlow.value = queue.toList()
    }

    fun remove(): T? {
        val item = if (queue.isNotEmpty()) queue.removeFirst() else null
        queueFlow.value = queue.toList()
        return item

    }

    fun asFlow(): Flow<List<T>> = queueFlow

}