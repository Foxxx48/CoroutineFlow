package com.foxxx.coroutineflow.lesson4.cryptoapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CryptoViewModel : ViewModel() {


    private val repository = CryptoRepository

    private val loadingFlow = MutableSharedFlow<CryptoState>()


    val state =
        repository.getCurrencyListFlow
            .filter { it.isNotEmpty() }
            .map { CryptoState.Content(cryptoCurrencyList = it) as CryptoState }
            .onStart {
                CryptoState.Loading
            }
            .mergeWith(loadingFlow)

    fun <T> Flow<T>.mergeWith (another: Flow<T>) : Flow<T> {
        return  merge(this, another)
    }


    fun refreshList() {
        viewModelScope.launch {
            loadingFlow.emit(CryptoState.Loading)
            repository.refreshList()
        }
    }

}




