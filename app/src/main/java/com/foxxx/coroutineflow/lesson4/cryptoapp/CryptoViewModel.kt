package com.foxxx.coroutineflow.lesson4.cryptoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class CryptoViewModel : ViewModel() {

    private val repository = CryptoRepository

    private val _state = MutableLiveData<CryptoState>(CryptoState.Initial)
    val state: LiveData<CryptoState> = _state

    init {
        loadData()
    }

    private fun loadData() {
        repository.getCurrencyListFlow()
            .onStart {
                val currentState = _state.value
                if (currentState !is CryptoState.Content || currentState.cryptoCurrencyList.isEmpty()) {
                    _state.value = CryptoState.Loading
                }
            }
            .filter { it.isNotEmpty() }
            .onEach {
                _state.value = CryptoState.Content(cryptoCurrencyList = it)
            }
            .launchIn(viewModelScope)

    }
}