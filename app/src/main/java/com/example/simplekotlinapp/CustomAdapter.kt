package com.example.simplekotlinapp

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.simplekotlinapp.databinding.ItemBinding


class CustomAdapter(val context: Context, val mList: ArrayList<DataModel>) : RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        holder.binding.title.text = mList[position].title
        holder.binding.description.text = mList[position].description
        holder.binding.date.text = mList[position].date
        holder.itemView.setOnClickListener{
            val intent = Intent(context, UpdatePage::class.java).addFlags(FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("id", mList[position].id)
            intent.putExtra("title", mList[position].title)
            intent.putExtra("description", mList[position].description)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class MyViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root){

    }
}