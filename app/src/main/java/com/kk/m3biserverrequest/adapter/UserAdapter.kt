package com.kk.m3biserverrequest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kk.m3biserverrequest.R
import com.kk.m3biserverrequest.models.Data

class UserAdapter : ListAdapter<Data, UserAdapter.userViewHolder>(DiffUtil()) {

    class userViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textName = view.findViewById<TextView>(R.id.textName)
        fun bind(item: Data) {
            textName.text = item.first_name + " " + item.last_name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return userViewHolder(view)
    }

    override fun onBindViewHolder(holder: userViewHolder, position: Int) {
        val item=getItem(position)
        holder.bind(item)
    }

    class DiffUtil :androidx.recyclerview.widget.DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem==newItem
        }
    }
}