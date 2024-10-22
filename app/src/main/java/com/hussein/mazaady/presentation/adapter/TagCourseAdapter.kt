package com.hussein.mazaady.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hussein.mazaady.R
import com.hussein.mazaady.databinding.TagItemLayoutBinding

class TagCourseAdapter (private val context: Context, private val items: ArrayList<String>) : RecyclerView.Adapter<TagCourseAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: TagItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    private var selectedPositions:String=""
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
       holder.binding.courseTitle.text = item
        //sey first item default to show selected style and you can click on any item to show selected style
        if((selectedPositions.isBlank() || selectedPositions.isEmpty())&& position == 0){
            selectedPositions = item
        }
        //Handle background color for selected item
        if(item==selectedPositions){
            holder.binding.lyContainer.setBackgroundResource(R.color.purple_200)
        }else{
            holder.binding.lyContainer.setBackgroundResource(R.color.gray_icon)
        }
        //Click listener for to change background color and selected item
        holder.binding.lyContainer.setOnClickListener {
            holder.binding.lyContainer.setBackgroundResource(R.color.purple_200)
            selectedPositions = item
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() = items.size
}