package com.foxxx.coroutineflow.lesson4.cryptoapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CryptoViewModel : ViewModel() {


    private val repository = CryptoRepository


    var state =
        repository.getCurrencyListFlow()
            .filter { it.isNotEmpty() }
            .map { CryptoState.Content(cryptoCurrencyList = it) as CryptoState }
            .onStart {
                CryptoState.Loading
            }


    fun refreshList() {
        viewModelScope.launch {
            repository.refreshList()
        }
    }

}




