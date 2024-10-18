package com.example.simplekotlinapp

import android.content.Context
import android.widget.Toast

class Utility {
    companion object{
        fun Toast(context: Context, text : String){
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
    }
}