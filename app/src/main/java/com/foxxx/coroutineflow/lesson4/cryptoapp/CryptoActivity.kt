package com.foxxx.coroutineflow.lesson4.cryptoapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.foxxx.coroutineflow.databinding.ActivityCryptoBinding

class CryptoActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityCryptoBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[CryptoViewModel::class.java]
    }

    private val adapter = CryptoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewCurrencyPriceList.adapter = adapter
        binding.recyclerViewCurrencyPriceList.itemAnimator = null
    }

    private fun observeViewModel() {
        viewModel.state.observe(this) {
            when (it) {
                is CryptoState.Initial -> {
                    binding.progressBarLoading.isVisible = false
                }
                is CryptoState.Loading -> {
                    binding.progressBarLoading.isVisible = true
                }
                is CryptoState.Content -> {
                    binding.progressBarLoading.isVisible = false
                    adapter.submitList(it.cryptoCurrencyList)
                }
            }
        }
    }

    companion object {

        fun newIntent(context: Context) = Intent(context, CryptoActivity::class.java)
    }
}