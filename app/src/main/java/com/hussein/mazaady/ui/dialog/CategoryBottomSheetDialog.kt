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
import com.hussein.mazaady.presentation.adapter.MainCategoryAdapter
import com.hussein.mazaady.presentation.event.OnCategoryListener
import com.hussein.mazaady.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryBottomSheetDialog : BottomSheetDialogFragment() , OnCategoryListener {
    private lateinit var binding: CategoryBottomSheetDialogBinding
    private lateinit var listener: OnCategoryListener
    val mainViewModel: MainViewModel by viewModel()
    private var categoriesLists : List<CategoryX> = ArrayList()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    // Use this method to create a new instance of the fragment
    companion object {
        const val TAG = "CategoryBottomSheetDialog"
        fun newInstance(listener: OnCategoryListener): CategoryBottomSheetDialog {
            val categoryBottomSheetDialog = CategoryBottomSheetDialog()
            categoryBottomSheetDialog.listener = listener
            return categoryBottomSheetDialog
        }
    }

    override fun getTheme(): Int {
        return R.style.RoundedBottomSheetDialog // Apply rounded border
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.category_bottom_sheet_dialog, container, false)
        binding = DataBindingUtil.bind(view)!!
        initView()
        callMainCategory()
        searchCategory()
        return binding.root
    }
    private fun initView(){
        binding.lyHeaderPopup.doneButton.setOnClickListener{
            dismiss()
        }
    }
    private fun callMainCategory(){
        binding.progress.visibility = View.VISIBLE
        mainViewModel.getCategories()
        coroutineScope.launch {
            mainViewModel.categoryData.collectLatest { it ->
                it.let {category ->
                    when(category.status) {
                        is Status.Success<*> -> {
                            category.data?.let {
                                categoriesLists = it.categories
                                val adapter =
                                    MainCategoryAdapter(
                                        it.categories,
                                        this@CategoryBottomSheetDialog
                                    )
                                binding.rvProperity.layoutManager = LinearLayoutManager(context)
                                binding.rvProperity.adapter = adapter
                            }
                            binding.progress.visibility = View.GONE
                        }
                        is Status.SuccessData<*> -> {
                            binding.progress.visibility = View.GONE
                            Toast.makeText(context, category.msg, Toast.LENGTH_SHORT).show()

                        }
                        is Status.Error -> {
                            binding.progress.visibility = View.GONE
                            Toast.makeText(context, category.msg, Toast.LENGTH_SHORT).show()

                        }

                        is Status.Loading -> {
                            binding.progress.visibility = View.VISIBLE
                        }
                    }
                }

            }

        }


    }
    private fun searchCategory() {
        binding.lyHeaderPopup.editText.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if(s.toString().isNotEmpty() && categoriesLists.isNotEmpty()){
                    val query = s.toString()
                    val filteredList = categoriesLists.filter { it.name!!.contains(query, ignoreCase = true) }
                    showRecyclerView(filteredList)
                }
                else{
                    showRecyclerView(categoriesLists)
                }
            }

        })

    }
    private fun showRecyclerView(categories: List<CategoryX>) {
        val adapter = MainCategoryAdapter(categories, this@CategoryBottomSheetDialog)
        binding.rvProperity.adapter = adapter
    }

    override fun onCategoryClicked(category: CategoryX) {
        listener.onCategoryClicked(category)
        dismiss()
    }
}