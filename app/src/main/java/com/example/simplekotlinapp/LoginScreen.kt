package com.example.simplekotlinapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.simplekotlinapp.databinding.ActivityLoginScreenBinding
import com.google.firebase.auth.FirebaseAuth

class LoginScreen : AppCompatActivity() {
    private lateinit var binding: ActivityLoginScreenBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        binding.loginButton.setOnClickListener {
            if (binding.userEmail.text.toString().isEmpty() && (binding.userPassword.text.toString()
                    .isEmpty())
            ) {
                Utility.Toast(applicationContext, "Please enter email and password")
            } else {
                login()
            }
        }

        binding.signupButton.setOnClickListener {
            startActivity(Intent(this, SignUpScreen::class.java))
        }
    }

    private fun login() {
        auth.signInWithEmailAndPassword(
            binding.userEmail.text.toString(),
            binding.userPassword.text.toString()
        ).addOnSuccessListener {
            Utility.Toast(applicationContext, "Login Successful")
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }.addOnFailureListener {
            Utility.Toast(applicationContext, "Login Failed")
        }
    }
}