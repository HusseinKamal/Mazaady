package com.hussein.mazaady.ui.dialog

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hussein.mazaady.R
import com.hussein.mazaady.data.model.Status
import com.hussein.mazaady.databinding.CategoryBottomSheetDialogBinding
import com.hussein.mazaady.domain.option.Option
import com.hussein.mazaady.domain.option.OptionX
import com.hussein.mazaady.domain.properity.Properity
import com.hussein.mazaady.presentation.adapter.OptionAdapter
import com.hussein.mazaady.presentation.adapter.SubOptionAdapter
import com.hussein.mazaady.presentation.event.OnOptionListener
import com.hussein.mazaady.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class OptionBottomSheetDialog : BottomSheetDialogFragment() , OnOptionListener {
    private lateinit var binding: CategoryBottomSheetDialogBinding
    private lateinit var listener: OnOptionListener
    private var selectedProperty: Properity? = null
    private var selectedOption: Option? = null
    private var selectedSubOption: OptionX? = null
    private var optionsList : List<Option> = ArrayList()
    val mainViewModel: MainViewModel by viewModel()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    // Use this method to create a new instance of the fragment
    companion object {
        const val TAG = "OptionBottomSheetDialog"
        fun newInstance(listener: OnOptionListener, selectedOption: Properity): OptionBottomSheetDialog {
            val bottomSheetDialog = OptionBottomSheetDialog()
            bottomSheetDialog.listener = listener
            bottomSheetDialog.selectedProperty = selectedOption
            return bottomSheetDialog
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogStyle3)
        isCancelable = false
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.category_bottom_sheet_dialog, container, false)
        binding = DataBindingUtil.bind(view)!!
        initView()
        showOptions()
        callOptionsPropertyAPI()
        searchCategory()
        return binding.root
    }
    private fun initView(){
        selectedProperty?.let { binding.lyHeaderPopup.titleTxt.text = it.name }
        binding.lyHeaderPopup.doneButton.setOnClickListener{
            dismiss()
        }
    }
    private fun showOptions(){
        binding.rvProperity.layoutManager = LinearLayoutManager(context)
        if (selectedProperty != null) {
            showRecyclerView(selectedProperty!!.options)
        }
        binding.progress.visibility = View.GONE

    }
    private fun searchCategory() {
        binding.lyHeaderPopup.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if(selectedProperty != null) {
                    if (s.toString().isNotEmpty() && selectedProperty!!.options.isNotEmpty()) {
                        val query = s.toString()
                        val filteredList =
                            selectedProperty!!.options.filter { it.name!!.contains(query, ignoreCase = true) }
                        showRecyclerView(filteredList)
                    }
                    else{
                        showRecyclerView(selectedProperty!!.options)
                    }
                }
            }

        })

    }
    private fun callOptionsPropertyAPI(){
        selectedOption?.let {
            mainViewModel.getOptions(it.id.toString())
            coroutineScope.launch {
                mainViewModel.optionData.collectLatest { it ->
                    it.let {property ->
                        when(property.status) {
                            is Status.SuccessData<*> -> {
                                optionsList= it.data
                                showRecyclerViewSubOption(optionsList)
                                binding.progress.visibility = View.GONE
                            }
                            is Status.Success<*> -> {
                                binding.progress.visibility = View.GONE
                                Toast.makeText(requireContext(), property.msg, Toast.LENGTH_SHORT).show()

                            }
                            is Status.Error -> {
                                binding.progress.visibility = View.GONE
                                Toast.makeText(requireContext(), property.msg, Toast.LENGTH_SHORT).show()

                            }
                            is Status.Loading -> {
                                binding.progress.visibility = View.VISIBLE
                            }
                        }
                    }

                }

            }
        }


    }
    private fun showRecyclerView(options: List<Option>) {

        if(options.isNotEmpty()){
            binding.rvProperity.visibility = View.VISIBLE
            val adapter =
                OptionAdapter(options,selectedProperty!!, this@OptionBottomSheetDialog)
            binding.rvProperity.adapter = adapter
        }
        else
        {
            binding.rvProperity.visibility = View.GONE
            binding.noDataTxt.visibility = View.VISIBLE
        }
    }
    private fun showRecyclerViewSubOption(options: List<Option>) {

        if(options.isNotEmpty()){
            if(options[0].options.isNotEmpty()) {
                binding.rvProperity.visibility = View.VISIBLE
                val adapter =
                    SubOptionAdapter(options[0].options, property = selectedProperty!!, this@OptionBottomSheetDialog)
                binding.rvProperity.adapter = adapter
            }
            else{
                dismiss()
            }
        }
        else
        {
            dismiss()
        }
    }


    override fun onOptionClicked(properity: Properity,option: Option) {
        listener.onOptionClicked(properity,option)
        selectedOption = option
        callOptionsPropertyAPI()
        //dismiss()
    }

    override fun onOptionClicked(properity: Properity,option: OptionX) {
        selectedSubOption = option
        listener.onOptionClicked(properity,selectedSubOption!!)
        dismiss()
    }

}