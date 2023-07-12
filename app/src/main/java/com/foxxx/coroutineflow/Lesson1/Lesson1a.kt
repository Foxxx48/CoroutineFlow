package com.foxxx.coroutineflow.Lesson1

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

suspend fun main() {
    val numbers = listOf(3, 4, 8, 16, 5, 7, 11, 32, 41, 28, 43, 47, 84, 116, 53, 59, 61).asFlow()
    numbers.filter { it.isPrimeS() }
        .filter { it > 20 }
        .map { "Number: $it" }
        .collect() { println(it) }
}


suspend fun Int.isPrimeS(): Boolean {
    if (this <= 1) return false

    for (i in 2..this / 2) {
        delay(50)
        if (this % i == 0) return false
    }
    return true
}