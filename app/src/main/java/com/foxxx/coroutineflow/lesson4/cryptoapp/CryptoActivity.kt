package com.foxxx.coroutineflow.lesson4.cryptoapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.foxxx.coroutineflow.databinding.ActivityCryptoBinding
import kotlinx.coroutines.launch

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

        binding.btnRefresh.setOnClickListener {
            Log.d("Crypto", "buttonRefresh Click")
            lifecycleScope.launch {
                viewModel.refreshList()
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewCurrencyPriceList.adapter = adapter
        binding.recyclerViewCurrencyPriceList.itemAnimator = null
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect() {
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
        }
    }

    companion object {

        fun newIntent(context: Context) = Intent(context, CryptoActivity::class.java)
    }
}