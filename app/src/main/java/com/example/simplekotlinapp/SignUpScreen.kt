package com.example.simplekotlinapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.simplekotlinapp.databinding.ActivitySignUpScreenBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpScreenBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.signupButton.setOnClickListener{
            signUp()
        }

        binding.loginButton.setOnClickListener{
            startActivity(Intent(this, LoginScreen::class.java))
        }
    }
    fun signUp(){
        binding.signupButton.setOnClickListener{
            if (binding.userEmail.text.isNotEmpty() && (binding.userPassword.text.isNotEmpty() == binding.userConformPassword.text.isNotEmpty())){
                auth.createUserWithEmailAndPassword(binding.userEmail.text.toString(), binding.userPassword.text.toString()).addOnSuccessListener{
                    Utility.Toast(applicationContext, "Sign Up Successful")
                    startActivity(Intent(this, LoginScreen::class.java))
                    finish()
                }.addOnFailureListener{
                    Utility.Toast(applicationContext, it.message.toString())
                }
            }else{
                Utility.Toast(applicationContext, "Please fill all the fields")
            }
        }
    }
}