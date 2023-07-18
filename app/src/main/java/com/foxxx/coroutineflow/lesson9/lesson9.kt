package com.foxxx.coroutineflow.lesson9

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

val coroutineScope = CoroutineScope(Dispatchers.IO)

private suspend fun main() {

    val flow = MutableSharedFlow<Int>()

    coroutineScope.launch {
        repeat(10) {
            println("Emitted: $it")
            flow.emit(it)
            delay(1000)
        }
    }

    val job1 = coroutineScope.launch() {
        flow.collect {
            println("Job1: $it")
        }
    }

    delay(5000)

    val job2 = coroutineScope.launch() {
        flow.collect {
            println("Job2: $it")
        }
    }

    job1.join()
    job2.join()
}

