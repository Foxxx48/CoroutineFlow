package com.foxxx.coroutineflow.lesson3

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

suspend fun main() {
    val numbers = getFlowByBuilderFlow()
    numbers.collect() {
        println(it)

    }



}

private fun getFlowByFlowOfBuilder(): Flow<Int> {
    return flowOf(3, 4, 8, 16, 5, 7, 11, 32, 41, 28, 43, 47, 84, 116, 53, 59, 61)
}

private fun getFlowByBuilderFlow(): Flow<Int> {
    val flowA = getFlowByFlowOfBuilder()
    return flow {
        flowA.collect {
            println("Emitted from first flow: $it")
            emit(it)
        }
    }
}

