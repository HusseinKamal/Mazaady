package com.hussein.mazaady.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hussein.mazaady.R
import com.hussein.mazaady.databinding.TagItemLayoutBinding

class TagsColoredAdapter (private val context: Context, private val items: ArrayList<String>) : RecyclerView.Adapter<TagsColoredAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: TagItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<TagItemLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.tag_item_layout,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        if(position % 2 == 0){
            holder.binding.lyContainer.setBackgroundResource(R.color.purple_200)
        }
        else{
            holder.binding.lyContainer.setBackgroundResource(R.color.tag_color)
        }
    }

    override fun getItemCount() = items.size
}