package com.foxxx.coroutineflow.lesson16

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retry

suspend fun main() {

    loadDataFlow()
        .map { AppState.Content(it) as AppState }
        .onStart { emit(AppState.Loading) }
        .retry(2) {
            println("Retry")
            true
        }
        .catch {
            emit(AppState.Error)
            println("$it")
        }
        .collect {
            when (it) {
                is AppState.Content -> {
                    println("Collected ${it.value}")
                }

                is AppState.Error -> {
                    println("Error. Something went wrong")
                }

                is AppState.Loading -> {
                    println("Loading...")
                }
            }

        }


}

fun loadDataFlow(): Flow<Int> = flow {
    repeat(6) {
        delay(1000)
        emit(it)
    }
    throw RuntimeException()
}

sealed class AppState() {
    data class Content(val value: Int) : AppState()
    object Error : AppState()
    object Loading : AppState()
}