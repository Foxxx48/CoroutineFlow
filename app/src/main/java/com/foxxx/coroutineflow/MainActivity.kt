package com.foxxx.coroutineflow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.foxxx.coroutineflow.databinding.ActivityMainBinding
import com.foxxx.coroutineflow.lesson12.TeamScoreActivity
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
            startActivity(UsersActivity.newIntent(this))
        }

        binding.buttonCryptoActivity.setOnClickListener {
            startActivity(CryptoActivity.newIntent(this))
        }

        binding.teamScoreActivity.setOnClickListener {
            startActivity(TeamScoreActivity.newIntent(this))
        }
    }
}