package com.hussein.mazaady.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hussein.mazaady.R
import com.hussein.mazaady.databinding.TableItemLayoutBinding
import com.hussein.mazaady.ui.home.PropertyMap

class TableAdapter (private val items: PropertyMap) : RecyclerView.Adapter<TableAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: TableItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<TableItemLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.table_item_layout,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items.keys.elementAt(position)
        val valueData = items[item]
        holder.binding.keyText.text = item.name
        valueData?.let { value ->
            value.value1.let { option ->
                holder.binding.valueText.text = option?.name
            }
        }
        if (valueData?.value2 != null) {
            valueData.let { value ->
                value.value2.let { option ->
                    option?.name.let {
                        holder.binding.valueText.append(" - " + option?.name)
                    }
                }
            }

        }

    }

    override fun getItemCount() = items.size
}