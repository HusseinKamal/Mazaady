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
import com.hussein.mazaady.domain.category.CategoryX
import com.hussein.mazaady.domain.category.Children
import com.hussein.mazaady.presentation.adapter.MainCategoryAdapter
import com.hussein.mazaady.presentation.adapter.SubCategoryAdapter
import com.hussein.mazaady.presentation.event.OnCategoryListener
import com.hussein.mazaady.presentation.event.OnSubCategoryListener
import com.hussein.mazaady.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SubCategoryBottomSheetDialog : BottomSheetDialogFragment() , OnSubCategoryListener {
    private lateinit var binding: CategoryBottomSheetDialogBinding
    private lateinit var listener: OnSubCategoryListener
    private var selectedCategory: CategoryX? = null

    // Use this method to create a new instance of the fragment
    companion object {
        const val TAG = "SubCategoryBottomSheetDialog"
        fun newInstance(listener: OnSubCategoryListener, selectedCategory: CategoryX): SubCategoryBottomSheetDialog {
            val categoryBottomSheetDialog = SubCategoryBottomSheetDialog()
            categoryBottomSheetDialog.listener = listener
            categoryBottomSheetDialog.selectedCategory = selectedCategory
            return categoryBottomSheetDialog
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
        showSubCategory()
        searchCategory()
        return binding.root
    }
    private fun initView(){
        selectedCategory?.let { binding.lyHeaderPopup.titleTxt.text = it.name }
        binding.lyHeaderPopup.doneButton.setOnClickListener{
            dismiss()
        }
    }
    private fun showSubCategory(){
        binding.rvProperity.layoutManager = LinearLayoutManager(context)
        if (selectedCategory != null) {
            val adapter =
                SubCategoryAdapter(
                    selectedCategory!!.children,
                    this@SubCategoryBottomSheetDialog
                )
            binding.rvProperity.adapter = adapter
        }
        binding.progress.visibility = View.GONE



    }
    private fun searchCategory() {
        binding.lyHeaderPopup.editText.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if(selectedCategory != null) {
                    if (s.toString().isNotEmpty() && selectedCategory!!.children.isNotEmpty()) {
                        val query = s.toString()
                        val filteredList =
                            selectedCategory!!.children.filter { it.name!!.contains(query, ignoreCase = true) }
                        showRecyclerView(filteredList)
                    }
                    else{
                        showRecyclerView(children = selectedCategory!!.children)
                    }
                }
            }

        })

    }
    private fun showRecyclerView(children: List<Children>) {
        val adapter =
            SubCategoryAdapter(children, this@SubCategoryBottomSheetDialog)
        binding.rvProperity.adapter = adapter
    }

    override fun onSubCategoryClicked(child: Children) {
        listener.onSubCategoryClicked(child)
        dismiss()
    }
}