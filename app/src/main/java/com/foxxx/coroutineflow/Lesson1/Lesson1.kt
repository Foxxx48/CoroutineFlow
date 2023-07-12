package com.foxxx.coroutineflow.Lesson1

fun main() {
    val numbers = listOf(3, 4, 8, 16, 5, 7, 11, 32, 41, 28, 43, 47, 84, 116, 53, 59, 61).asSequence()
    numbers.filter { it.isPrime() }
        .filter { it > 20 }
        .map { "Number: $it" }
        .forEach { println(it) }

}


fun Int.isPrime(): Boolean {
    if (this <= 1) return false

    for (i in 2..this / 2) {
        Thread.sleep(50)
        if (this % i == 0) return false
    }
    return true
}