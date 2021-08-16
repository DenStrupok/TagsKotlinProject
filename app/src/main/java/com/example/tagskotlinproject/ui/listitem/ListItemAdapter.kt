package com.example.tagskotlinproject.ui.listitem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tagskotlinproject.R
import com.example.tagskotlinproject.pojo.Results

class ListItemAdapter : RecyclerView.Adapter<ListItemAdapter.ViewHolder>() {



    private var listResult = mutableListOf<Results>()
    fun sendListResult(listItems: MutableList<Results>) {
        this.listResult = listItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_result_recycler_view, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvAuthor.text = listResult[position].author?.username
        Glide.with(holder.imgPicture).load(listResult[position].preview?.src).into(holder.imgPicture)
    }

    override fun getItemCount(): Int {
        return listResult.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPicture: ImageView = itemView.findViewById(R.id.imgPicture)
        val tvAuthor: TextView = itemView.findViewById(R.id.tvAuthor)
    }
}

