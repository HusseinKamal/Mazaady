package com.hussein.mazaady.presentation.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.hussein.mazaady.R
import com.hussein.mazaady.databinding.ProperityItemLayoutBinding
import com.hussein.mazaady.domain.Util
import com.hussein.mazaady.domain.option.Option
import com.hussein.mazaady.domain.option.OptionX
import com.hussein.mazaady.domain.properity.Properity
import com.hussein.mazaady.presentation.event.OnOptionListener
import com.hussein.mazaady.presentation.event.OnProperityListener
import com.hussein.mazaady.ui.dialog.OptionBottomSheetDialog

class PropertyAdapter(private val fragmentManager: FragmentManager, private val items: List<Properity>, private val listener: OnProperityListener,private val listenerOptions: OnOptionListener) : RecyclerView.Adapter<PropertyAdapter.ViewHolder>(){

    init {
        if(items.isNotEmpty()){
            items[0].isVisible = true
        }
    }
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
        holder.binding.root.visibility = if(item.isVisible) ViewGroup.VISIBLE else ViewGroup.GONE
        holder.binding.fieldMainCategory.ViewPopup.setOnClickListener {
            val bottomSheetDialogFragment = OptionBottomSheetDialog.newInstance(object : OnOptionListener {
                override fun onOptionClicked(properity: Properity,option: Option) {
                    if(option.name.equals(Util.OTHER_OPTION))
                    {
                        holder.binding.lyOtherOption.visibility = ViewGroup.VISIBLE
                        holder.binding.btnSubmit.setOnClickListener{
                            holder.binding.textOther.clearFocus()
                            option.name = holder.binding.textOther.text.toString()
                            listenerOptions.onOptionClicked(properity, option)
                            selectedProperity = item
                            listener.onProperityClicked(item)
                            if (selectedOption.contains(option)) {
                                selectedOption.remove(option)
                            } else {
                                selectedOption.add(option)
                            }
                            holder.binding.fieldMainCategory.mainCategoryField.text =
                                item.name + " , " + option.name
                        }
                    }
                    else {
                        //User Choose Option
                        listenerOptions.onOptionClicked(properity, option)
                        selectedProperity = item
                        listener.onProperityClicked(item)
                        if (selectedOption.contains(option)) {
                            selectedOption.remove(option)
                        } else {
                            selectedOption.add(option)
                        }
                        holder.binding.fieldMainCategory.mainCategoryField.text =
                            item.name + " , " + option.name
                    }
                    //Show next one
                    showNextItem(position)

                }

                override fun onOptionClicked(properity: Properity,option: OptionX) {
                    if(option.name.equals(Util.OTHER_OPTION))
                    {
                        holder.binding.lyOtherOption.visibility = ViewGroup.VISIBLE
                        holder.binding.btnSubmit.setOnClickListener{
                            holder.binding.textOther.clearFocus()
                            option.name = holder.binding.textOther.text.toString()
                            listenerOptions.onOptionClicked(properity, option)
                            selectedProperity = item
                            listener.onProperityClicked(item)
                            if (selectedSubOption.contains(option)) {
                                selectedSubOption.remove(option)
                            } else {
                                selectedSubOption.add(option)
                            }
                            holder.binding.fieldMainCategory.mainCategoryField.text =
                                item.name + " , " + option.name
                        }
                    }
                    else {
                        //User Choose SubOption
                        listenerOptions.onOptionClicked(properity, option)
                        selectedProperity = item
                        listener.onProperityClicked(item)
                        if (selectedSubOption.contains(option)) {
                            selectedSubOption.remove(option)
                        } else {
                            selectedSubOption.add(option)
                        }
                        holder.binding.fieldMainCategory.mainCategoryField.text =
                            item.name + " , " + option.name
                    }
                    //Show next one
                    showNextItem(position)
                }
            },item)
            bottomSheetDialogFragment.show(fragmentManager, OptionBottomSheetDialog.TAG)
        }

    }
    private fun showNextItem(position: Int){
        if(position+1 < items.size){
            for(i in position+1 until items.size){
                if(items[i].options.isNotEmpty()){
                    items[i].isVisible = true
                    notifyItemChanged(i)
                    break
                }
                else{
                    items[i].isVisible = true
                    notifyItemChanged(i)
                }
            }
        }
    }

    override fun getItemCount() = items.size

}