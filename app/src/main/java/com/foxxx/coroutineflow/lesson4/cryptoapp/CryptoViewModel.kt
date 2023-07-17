package com.foxxx.coroutineflow.lesson4.cryptoapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class CryptoViewModel : ViewModel() {

    private val repository = CryptoRepository

    val state =
        repository.getCurrencyListFlow()
            .filter { it.isNotEmpty() }
            .map { CryptoState.Content(cryptoCurrencyList = it) as CryptoState }
            .onStart {
                CryptoState.Loading
            }
            .asLiveData()

}



