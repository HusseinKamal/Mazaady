package com.hussein.mazaady.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hussein.mazaady.R
import com.hussein.mazaady.databinding.ItemVideoCallProfileBinding

class ActiveVideoCallProfileAdapter (private val context: Context, private val items: ArrayList<String>) : RecyclerView.Adapter<ActiveVideoCallProfileAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemVideoCallProfileBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemVideoCallProfileBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_video_call_profile,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
       holder.binding.icProfileImage.setImageResource(R.drawable.avatarlive)
    }

    override fun getItemCount() = items.size
}