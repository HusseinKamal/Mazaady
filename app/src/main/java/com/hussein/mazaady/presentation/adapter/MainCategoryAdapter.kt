package com.hussein.mazaady.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hussein.mazaady.R
import com.hussein.mazaady.databinding.MainCategoryItemLayoutBinding
import com.hussein.mazaady.databinding.ProperityItemLayoutBinding
import com.hussein.mazaady.domain.category.Category
import com.hussein.mazaady.domain.category.CategoryX
import com.hussein.mazaady.domain.properity.Properity
import com.hussein.mazaady.presentation.event.OnCategoryListener
import com.hussein.mazaady.presentation.event.OnProperityListener

public class MainCategoryAdapter (private val items: List<CategoryX>, private val listener: OnCategoryListener) : RecyclerView.Adapter<MainCategoryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: MainCategoryItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<MainCategoryItemLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.main_category_item_layout,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.item = item
        holder.itemView.setOnClickListener {
            listener.onCategoryClicked(item)
        }
    }

    override fun getItemCount() = items.size
}