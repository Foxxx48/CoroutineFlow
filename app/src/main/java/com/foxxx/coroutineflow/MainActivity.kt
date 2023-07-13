package com.foxxx.coroutineflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.foxxx.coroutineflow.databinding.ActivityMainBinding
import com.foxxx.coroutineflow.lesson2.UsersActivity

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
    }
}