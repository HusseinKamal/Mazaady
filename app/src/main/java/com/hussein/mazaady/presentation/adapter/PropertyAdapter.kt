package com.hussein.mazaady.presentation.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.hussein.mazaady.R
import com.hussein.mazaady.databinding.ProperityItemLayoutBinding
import com.hussein.mazaady.domain.option.Option
import com.hussein.mazaady.domain.option.OptionX
import com.hussein.mazaady.domain.properity.Properity
import com.hussein.mazaady.presentation.event.OnOptionListener
import com.hussein.mazaady.presentation.event.OnProperityListener
import com.hussein.mazaady.ui.dialog.OptionBottomSheetDialog

class PropertyAdapter(private val fragmentManager: FragmentManager, private val items: List<Properity>, private val listener: OnProperityListener,private val listenerOptions: OnOptionListener) : RecyclerView.Adapter<PropertyAdapter.ViewHolder>(){

    private var selectedOption:ArrayList<Option> = ArrayList()
    private var selectedSubOption:ArrayList<OptionX> = ArrayList()

    private var selectedProperity:Properity ?= null
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
        holder.binding.fieldMainCategory.mainCategoryField.text = item.name
        holder.binding.fieldMainCategory.hintText.text = item.name
        holder.binding.fieldMainCategory.ViewPopup.setOnClickListener {
            val bottomSheetDialogFragment = OptionBottomSheetDialog.newInstance(object : OnOptionListener {
                override fun onOptionClicked(properity: Properity,option: Option) {
                    listenerOptions.onOptionClicked(properity,option)
                    selectedProperity = item
                    listener.onProperityClicked(item)
                    if(selectedOption.contains(option)){
                        selectedOption.remove(option)
                    }else{
                        selectedOption.add(option)
                    }
                    holder.binding.fieldMainCategory.mainCategoryField.text =   item.name + " , "+ option.name
                    //notifyDataSetChanged()

                }

                override fun onOptionClicked(properity: Properity,option: OptionX) {
                    listenerOptions.onOptionClicked(properity,option)
                    selectedProperity = item
                    listener.onProperityClicked(item)
                    if(selectedSubOption.contains(option)){
                        selectedSubOption.remove(option)
                    }else{
                        selectedSubOption.add(option)
                    }
                    holder.binding.fieldMainCategory.mainCategoryField.text =   item.name + " , "+ option.name
                }
            },item)
            bottomSheetDialogFragment.show(fragmentManager, OptionBottomSheetDialog.TAG)
        }

    }

    override fun getItemCount() = items.size

}