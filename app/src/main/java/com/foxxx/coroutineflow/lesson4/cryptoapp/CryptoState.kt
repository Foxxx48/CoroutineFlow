package com.foxxx.coroutineflow.lesson4.cryptoapp

sealed class CryptoState {
    object Initial : CryptoState()
    object Loading : CryptoState()
    data class Content(val cryptoCurrencyList: List<CryptoCurrency>) : CryptoState()
}
