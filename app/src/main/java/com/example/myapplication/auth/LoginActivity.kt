package com.example.myapplication.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.main.MainActivity
import com.example.myapplication.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import androidx.activity.viewModels



class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (validateForm(email, password)) {
                loginViewModel.login(email, password)
                Log.d("LoginActivity", "Login button clicked with email: $email")
            }
        }

        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (validateForm(email, password)) {
                loginViewModel.register(email, password)
                Log.d("LoginActivity", "Register button clicked with email: $email")
            }
        }
    }

    private fun setupObservers() {
        loginViewModel.loginResult.observe(this, Observer { success ->
            if (success) {
                navigateToMainScreen()
            } else {
                showLoginFailed()
            }
        })

        loginViewModel.user.observe(this, Observer { user ->
            if (user != null) {
                navigateToMainScreen()
            }
        })
    }

    private fun validateForm(email: String, password: String): Boolean {
        var valid = true

        if (TextUtils.isEmpty(email)) {
            binding.etEmail.error = "Required."
            valid = false
        } else {
            binding.etEmail.error = null
        }

        if (TextUtils.isEmpty(password)) {
            binding.etPassword.error = "Required."
            valid = false
        } else {
            binding.etPassword.error = null
        }

        return valid
    }

    private fun navigateToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showLoginFailed() {
        Snackbar.make(binding.root, "Authentication failed.", Snackbar.LENGTH_SHORT).show()
    }
}