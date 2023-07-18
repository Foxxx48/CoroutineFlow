package com.foxxx.coroutineflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.foxxx.coroutineflow.databinding.ActivityMainBinding
import com.foxxx.coroutineflow.lesson2.UsersActivity
import com.foxxx.coroutineflow.lesson4.cryptoapp.CryptoActivity

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonUsersActivity.setOnClickListener {
            println("buttonCryptoActivity Click")
            startActivity(UsersActivity.newIntent(this))
        }

        binding.buttonCryptoActivity.setOnClickListener {
            Log.d("Crypto", "buttonCryptoActivity Click")
            startActivity(CryptoActivity.newIntent(this))
        }
    }
}