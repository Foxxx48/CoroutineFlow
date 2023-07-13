package com.foxxx.coroutineflow.lesson2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.foxxx.coroutineflow.databinding.ActivityUsersBinding

class UsersActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityUsersBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[UsersViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupListeners()
        observeViewModel()
        clearEditText()
    }

    private fun setupListeners() {
        binding.buttonAddUser.setOnClickListener {
            binding.editTextUsername.text.toString()
                .trim()
                .takeIf { it.isNotBlank() }
                ?.let {
                    viewModel.addUser(it)
                }

            clearEditText()
        }
        binding.buttonNextScreen.setOnClickListener {
            startActivity(Users2Activity.newIntent(this))
        }
    }

    private fun observeViewModel() {
        viewModel.users.observe(this) {
            binding.textViewUsers.text = it.joinToString()
        }
    }

    private fun clearEditText() {
        binding.editTextUsername.text.clear()
    }

    companion object {

        fun newIntent(context: Context) = Intent(context, UsersActivity::class.java)
    }
}