package com.foxxx.coroutineflow.lesson8

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

val coroutineScope = CoroutineScope(Dispatchers.IO)

suspend fun main() {

    val flow = getNumbersFlow()

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

suspend fun getNumbersFlow(): Flow<Int> = flow {
    repeat(25) {
        println("Emited: $it")
        emit(it)
        delay(1000)
    }
}