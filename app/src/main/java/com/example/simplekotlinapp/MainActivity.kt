package com.example.simplekotlinapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplekotlinapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database : FirebaseFirestore
    private lateinit var adapter : CustomAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var mList: ArrayList<DataModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        setSupportActionBar(binding.toolBar).apply {
            title = "All Notes"
        }


        binding.addNotes.setOnClickListener{
            startActivity(Intent(this, InsertPage::class.java))
        }

        mList = ArrayList()
        getData()
        adapter = CustomAdapter(applicationContext, mList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter

    }
    private fun getData(){
        val user = auth.currentUser
        mList.clear()
        database.collection(user!!.uid).get().addOnSuccessListener {
            binding.progressBar.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            for (document in it){
                val data = document.toObject(DataModel::class.java)
                mList.add(data)
            }
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.logout){
            AlertDialog.Builder(this).apply {
                setTitle("Logout")
                setMessage("Are you sure want to logout?")
                setPositiveButton("Yes"){_,_ ->
                    auth.signOut()
                    startActivity(Intent(this@MainActivity, LoginScreen::class.java))
                    finish()
                }
                setNegativeButton("No"){a,b ->
                    a.dismiss()
                }
                show()
            }
        }
        return true
    }

}