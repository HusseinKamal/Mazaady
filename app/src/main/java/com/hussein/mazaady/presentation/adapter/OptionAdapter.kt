package com.hussein.mazaady.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hussein.mazaady.R
import com.hussein.mazaady.databinding.OptionItemLayoutBinding
import com.hussein.mazaady.databinding.ProperityItemLayoutBinding
import com.hussein.mazaady.domain.option.OptionX
import com.hussein.mazaady.domain.properity.Properity
import com.hussein.mazaady.presentation.event.OnOptionListener
import com.hussein.mazaady.presentation.event.OnProperityListener

public class OptionAdapter (private val items: List<OptionX>, private val listener: OnOptionListener) : RecyclerView.Adapter<OptionAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: OptionItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<OptionItemLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.option_item_layout,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.item = item
        holder.itemView.setOnClickListener {
            listener.onOptionClicked(item)
        }
    }

    override fun getItemCount() = items.size
}