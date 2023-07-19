package com.foxxx.coroutineflow.lesson4.cryptoapp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlin.random.Random

object CryptoRepository {

    private val cryptoCurrencyNames = listOf("BTC", "ETH", "USDT", "BNB", "USDC")
    private val cryptoCurrencyList = mutableListOf<CryptoCurrency>()

    private val scope = CoroutineScope(Dispatchers.Default)

    private val refreshEvents = MutableSharedFlow<Unit>()


    val getCurrencyListFlow = flow {
        delay(3000)
        getCurrencyList()
        emit(cryptoCurrencyList.toList())
        refreshEvents.collect {
            delay(3000)
            generateCurrencyList()
            emit(cryptoCurrencyList.toList())
        }
    }.stateIn(
        scope = scope,
        started = SharingStarted.Lazily,
        initialValue = cryptoCurrencyList.toList()
    )

    suspend fun refreshList() {
        refreshEvents.emit(Unit)
    }

    private suspend fun getCurrencyList(): List<CryptoCurrency> {
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