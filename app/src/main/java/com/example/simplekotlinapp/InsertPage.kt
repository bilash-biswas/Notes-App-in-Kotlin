package com.example.simplekotlinapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.simplekotlinapp.databinding.ActivityInsertPageBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class InsertPage : AppCompatActivity() {
    private lateinit var database: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityInsertPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.firestore
        auth = FirebaseAuth.getInstance()


        binding.addButton.setOnClickListener {
            addNotes()
        }

    }

    private fun addNotes() {
        val user = auth.currentUser

        Log.d("user", user.toString())
        val documentId = database.collection(user!!.uid).document().id
        val data = DataModel(
            documentId,
            binding.title.text.toString(),
            binding.description.text.toString(),
            getCurrentTime()
        )
        database.collection(user.uid).document(documentId).set(data).addOnSuccessListener {
            Utility.Toast(applicationContext, "Note is successfully added")
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }.addOnFailureListener {
            Utility.Toast(applicationContext, it.message.toString())
        }
    }

    private fun getCurrentTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return dateFormat.format(Date())
    }
}