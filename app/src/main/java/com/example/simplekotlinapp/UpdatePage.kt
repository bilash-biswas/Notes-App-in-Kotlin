package com.example.simplekotlinapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.simplekotlinapp.databinding.ActivityUpdatePageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UpdatePage : AppCompatActivity() {
    private lateinit var binding: ActivityUpdatePageBinding
    private lateinit var database: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdatePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        binding.title.setText(intent.getStringExtra("title"))
        binding.description.setText(intent.getStringExtra("description"))

        binding.updateButton.setOnClickListener {
            updateData()
        }

        binding.deleteButton.setOnClickListener{
            AlertDialog.Builder(this).apply {
                setTitle("Delete Notes")
                setMessage("Are you sure you want to delete this note?")
                setPositiveButton("Yes") { _, _ ->
                    deleteData()
                }
                setNegativeButton("No", null)
                show()
            }
        }
    }

    private fun updateData() {
        val user = auth.currentUser
        if (user != null) {
            val documentId = intent.getStringExtra("id")
            val map = mapOf(
                "title" to binding.title.text.toString(),
                "description" to binding.description.text.toString()
            )

            if (documentId != null) {
                database.collection(user.uid).document(documentId).update(map)
                    .addOnSuccessListener {
                        Utility.Toast(applicationContext, "Updated Successfully")
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                    .addOnFailureListener {
                        Utility.Toast(applicationContext, "Failed to update")
                    }
            } else {
                Utility.Toast(applicationContext, "No document ID provided")
            }
        } else {
            Utility.Toast(applicationContext, "User not authenticated")
        }
    }

    private fun deleteData(){
        val user = auth.currentUser
        val documentId = intent.getStringExtra("id")
        database.collection(user!!.uid).document(documentId!!).delete().addOnSuccessListener {
            Utility.Toast(applicationContext, "Deleted Successfully")
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }.addOnFailureListener {
            Utility.Toast(applicationContext, "Failed to delete")
        }
    }
}
