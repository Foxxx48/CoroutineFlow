package com.foxxx.coroutineflow.lesson4.cryptoapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foxxx.coroutineflow.R
import com.foxxx.coroutineflow.databinding.CryptoItemBinding

class CryptoAdapter :
    ListAdapter<CryptoCurrency, CryptoAdapter.CryptoViewHolder>(CryptoDiffCallback) {

    class CryptoViewHolder(val binding: CryptoItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val binding = CryptoItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CryptoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val context = holder.binding.root.context
        val cryptoCurrency = getItem(position)
        holder.binding.textViewCurrencyName.text = cryptoCurrency.name
        holder.binding.textViewCurrencyPrice.text = context.getString(
            R.string.currency_price,
            "${cryptoCurrency.price}"
        )
    }

}

private object CryptoDiffCallback : DiffUtil.ItemCallback<CryptoCurrency>() {
    override fun areItemsTheSame(oldItem: CryptoCurrency, newItem: CryptoCurrency): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: CryptoCurrency, newItem: CryptoCurrency): Boolean {
        return oldItem == newItem
    }
}