package com.hussein.mazaady.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hussein.mazaady.R
import com.hussein.mazaady.databinding.OptionItemLayoutBinding
import com.hussein.mazaady.databinding.SubOptionItemLayoutBinding
import com.hussein.mazaady.domain.option.Option
import com.hussein.mazaady.domain.option.OptionX
import com.hussein.mazaady.domain.properity.Properity
import com.hussein.mazaady.presentation.event.OnOptionListener

class SubOptionAdapter (private val items: List<OptionX>, private val property: Properity, private val listener: OnOptionListener) : RecyclerView.Adapter<SubOptionAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: SubOptionItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<SubOptionItemLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.sub_option_item_layout,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.item = item
        holder.itemView.setOnClickListener {
            listener.onOptionClicked(property,item)
        }
    }

    override fun getItemCount() = items.size
}