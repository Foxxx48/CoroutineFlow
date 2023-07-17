package com.foxxx.coroutineflow.lesson4.cryptoapp

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

object CryptoRepository {

    private val cryptoCurrencyNames = listOf("BTC", "ETH", "USDT", "BNB", "USDC")
    private val cryptoCurrencyList = mutableListOf<CryptoCurrency>()

    fun getCurrencyListFlow() : Flow<List<CryptoCurrency>> = flow {
        while (true) {
            getCurrencyList()
            emit(cryptoCurrencyList.toList())
            delay(500)
        }
    }
    suspend fun getCurrencyList(): List<CryptoCurrency> {
        delay(3000)
        generateCurrencyList()
        return cryptoCurrencyList.toList()
    }

    private fun generateCurrencyList() {
        val prices = buildList {
            repeat(cryptoCurrencyNames.size) {
                add(Random.nextInt(1000, 2000))
            }
        }

        val newData = buildList {
            for ((index, currencyName) in cryptoCurrencyNames.withIndex()) {
                val price = prices[index]
                val currency = CryptoCurrency(name = currencyName, price = price)
                add(currency)
            }
        }
        cryptoCurrencyList.clear()
        cryptoCurrencyList.addAll(newData)
    }
}