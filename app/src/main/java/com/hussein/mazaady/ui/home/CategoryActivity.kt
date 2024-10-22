package com.hussein.mazaady.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hussein.mazaady.R
import com.hussein.mazaady.databinding.ActivityCategoryBinding
import com.hussein.mazaady.domain.category.CategoryX
import com.hussein.mazaady.domain.category.Children
import com.hussein.mazaady.domain.option.OptionX
import com.hussein.mazaady.domain.properity.Properity
import com.hussein.mazaady.presentation.event.OnCategoryListener
import com.hussein.mazaady.presentation.event.OnOptionListener
import com.hussein.mazaady.presentation.event.OnProperityListener
import com.hussein.mazaady.presentation.event.OnSubCategoryListener
import com.hussein.mazaady.ui.dialog.CategoryBottomSheetDialog

class CategoryActivity : AppCompatActivity(), OnCategoryListener, OnSubCategoryListener,
    OnProperityListener,OnOptionListener {
    private lateinit var binding: ActivityCategoryBinding
    private var currentMainCategory: CategoryX? = null
    private var currentSubCategory: Children? = null
    private var currentProperity: Properity? = null
    private var currentOption: List<OptionX> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //Data binding for activity
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Setup Insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initViews()
    }
    private fun initViews() {
        binding.fieldMainCategory.mainCategoryField.setText(resources.getString(R.string.main_category))
        binding.fieldSubMainCategory.mainCategoryField.setText(resources.getString(R.string.sub_category))
        binding.fieldMainCategory.ViewPopup.setOnClickListener{
            //Show Main Categories
            val bottomSheetDialogFragment = CategoryBottomSheetDialog.newInstance(this@CategoryActivity)
            bottomSheetDialogFragment.show(supportFragmentManager, CategoryBottomSheetDialog.TAG)
        }

        binding.lyTableContainer.gotoHomeBtn.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onCategoryClicked(category: CategoryX) {
        //Show Sub Categories
        currentMainCategory = category
        currentMainCategory.let {
            binding.fieldMainCategory.mainCategoryField.setText(it?.name)
        }
    }

    override fun onSubCategoryClicked(child: Children) {
        TODO("Not yet implemented")
    }

    override fun onProperityClicked(properity: Properity) {
        TODO("Not yet implemented")
    }

    override fun onOptionClicked(option: OptionX) {
        TODO("Not yet implemented")
    }

}