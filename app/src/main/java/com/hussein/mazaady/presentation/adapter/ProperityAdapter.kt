package com.hussein.mazaady.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hussein.mazaady.R
import com.hussein.mazaady.databinding.ProperityItemLayoutBinding
import com.hussein.mazaady.domain.properity.Properity
import com.hussein.mazaady.presentation.event.OnProperityListener

public class ProperityAdapter (private val items: List<Properity> , private val listener: OnProperityListener) : RecyclerView.Adapter<ProperityAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ProperityItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ProperityItemLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.properity_item_layout,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.fieldMainCategory.mainCategoryField.setText(item.name)
        holder.itemView.setOnClickListener {
            listener.onProperityClicked(item)
        }
    }

    override fun getItemCount() = items.size
}