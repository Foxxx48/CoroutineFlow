package com.foxxx.coroutineflow.lesson4.cryptoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CryptoViewModel : ViewModel() {

    private val repository = CryptoRepository

    private val _state = MutableLiveData<CryptoState>(CryptoState.Initial)
    val state: LiveData<CryptoState> = _state

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            while (true) {
                val currentState = _state.value
                if (currentState !is CryptoState.Content || currentState.cryptoCurrencyList.isEmpty()) {
                    _state.value = CryptoState.Loading
                }
                val currencyList = repository.getCurrencyList()
                _state.value = CryptoState.Content(cryptoCurrencyList = currencyList)
                delay(3000)
            }
        }
    }
}