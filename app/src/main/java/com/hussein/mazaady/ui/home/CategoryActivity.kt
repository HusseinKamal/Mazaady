package com.hussein.mazaady.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.hussein.mazaady.R
import com.hussein.mazaady.data.model.Status
import com.hussein.mazaady.databinding.ActivityCategoryBinding
import com.hussein.mazaady.domain.category.CategoryX
import com.hussein.mazaady.domain.category.Children
import com.hussein.mazaady.domain.option.Option
import com.hussein.mazaady.domain.option.OptionX
import com.hussein.mazaady.domain.properity.KeyValuePair
import com.hussein.mazaady.domain.properity.Properity
import com.hussein.mazaady.presentation.adapter.PropertyAdapter
import com.hussein.mazaady.presentation.adapter.TableAdapter
import com.hussein.mazaady.presentation.event.OnCategoryListener
import com.hussein.mazaady.presentation.event.OnOptionListener
import com.hussein.mazaady.presentation.event.OnProperityListener
import com.hussein.mazaady.presentation.event.OnSubCategoryListener
import com.hussein.mazaady.presentation.viewmodel.MainViewModel
import com.hussein.mazaady.ui.dialog.CategoryBottomSheetDialog
import com.hussein.mazaady.ui.dialog.SubCategoryBottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

typealias PropertyMap = HashMap<Properity, KeyValuePair>
class CategoryActivity : AppCompatActivity(), OnCategoryListener, OnSubCategoryListener,
    OnProperityListener,OnOptionListener {
    private lateinit var binding: ActivityCategoryBinding
    private var currentMainCategory: CategoryX? = null
    private var currentSubCategory: Children? = null
    private var currentProperity: Properity? = null
    private var currentOption: Option? = null
    private var currentSubOption: OptionX? = null
    private var propertyList: List<Properity> = ArrayList()
    val mainViewModel: MainViewModel by viewModel()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var propertyMap:PropertyMap = HashMap()

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
        binding.fieldMainCategory.mainCategoryField.text = resources.getString(R.string.main_category)
        binding.fieldMainCategory.hintText.text = resources.getString(R.string.main_category)
        binding.fieldSubMainCategory.mainCategoryField.text = resources.getString(R.string.sub_category)
        binding.fieldSubMainCategory.hintText.text = resources.getString(R.string.sub_category)
        //Show Main Categories
        binding.fieldMainCategory.ViewPopup.setOnClickListener{
            val bottomSheetDialogFragment = CategoryBottomSheetDialog.newInstance(this@CategoryActivity)
            bottomSheetDialogFragment.show(supportFragmentManager, CategoryBottomSheetDialog.TAG)
        }

        //Show Sub Categories
        binding.fieldSubMainCategory.ViewPopup.setOnClickListener{
            currentMainCategory?.let {
                val bottomSheetDialogFragment = SubCategoryBottomSheetDialog.newInstance(this@CategoryActivity,it)
                bottomSheetDialogFragment.show(supportFragmentManager, SubCategoryBottomSheetDialog.TAG)
            }

        }

        //Show table of properties
        binding.btnSubmit.setOnClickListener {
            binding.lyTableContainer.root.visibility = View.VISIBLE
            binding.fieldMainCategory.root.visibility = View.GONE
            binding.fieldSubMainCategory.root.visibility = View.GONE
            binding.rvProperity.visibility = View.GONE
            binding.btnSubmit.visibility = View.GONE

            //load table of selected properties
            val adapter = TableAdapter(propertyMap)
            binding.lyTableContainer.rvTable.layoutManager = LinearLayoutManager(applicationContext)
            binding.lyTableContainer.rvTable.adapter = adapter
        }

        //Button listener to go to Home page
        binding.lyTableContainer.gotoHomeBtn.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onCategoryClicked(category: CategoryX) {
        //Show Main Categories
        currentMainCategory = category
        currentMainCategory.let {
            binding.fieldMainCategory.mainCategoryField.text = it?.name
            binding.fieldSubMainCategory.mainCategoryField.text = ""
        }

    }
    private fun addMainCategoryToPropertyMap()
    {
        if(currentMainCategory != null) {
            //Add Main Category to Property Map and show in table
            val property = Properity(
                id = currentMainCategory!!.id,
                name = resources.getString(R.string.main_category_ara),
                type = currentMainCategory!!.slug
            )
            val option = Option(
                id = currentMainCategory!!.id,
                name = currentMainCategory!!.name,
                parent = currentMainCategory!!.id
            )
            if (propertyMap.containsKey(property)) {
                propertyMap.remove(property)
            }
            propertyMap[property] = KeyValuePair(option, null)
        }
    }
    private fun addSubCategoryToPropertyMap(){
        if(currentSubCategory != null) {
            //Add Main Category to Property Map and show in table
            val property = Properity(
                id = currentSubCategory!!.id,
                name = resources.getString(R.string.sub_category_ara),
                type = currentSubCategory!!.slug
            )
            val option = Option(
                id = currentSubCategory!!.id,
                name = currentSubCategory!!.name,
                parent = currentSubCategory!!.id
            )
            if (propertyMap.containsKey(property)) {
                propertyMap.remove(property)
            }
            propertyMap[property] = KeyValuePair(option, null)
        }
    }

    override fun onSubCategoryClicked(child: Children) {
        //Show Sub Categories
        currentSubCategory = child
        currentSubCategory.let {
            binding.fieldSubMainCategory.mainCategoryField.setText(it?.name)
            callPropertiesAPI(it?.id.toString())
        }
        addSubCategoryToPropertyMap()
        addMainCategoryToPropertyMap()
    }

    override fun onProperityClicked(properity: Properity) {
        currentProperity = properity
    }

    override fun onOptionClicked(properity: Properity,option: Option) {
        currentProperity = properity
        currentOption = option
        saveSelectedProperties()
    }

    override fun onOptionClicked(properity: Properity,option: OptionX) {
        currentProperity = properity
        currentSubOption = option
        saveSelectedProperties()
    }
    private fun saveSelectedProperties() {
        if(propertyMap.containsKey(currentProperity!!)) {
            propertyMap.remove(currentProperity)
        }
        propertyMap[currentProperity!!] = KeyValuePair(currentOption, currentSubOption)
    }

    private fun callPropertiesAPI(id:String){
        mainViewModel.getProperities(id)
        coroutineScope.launch {
            mainViewModel.properityData.collectLatest { it ->
                it.let {property ->
                    when(property.status) {
                        is Status.SuccessData<*> -> {
                            propertyList= it.data
                            showProperty()
                            binding.progressBar.visibility = View.GONE
                        }
                        is Status.Success<*> -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(applicationContext, property.msg, Toast.LENGTH_SHORT).show()

                        }
                        is Status.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(applicationContext, property.msg, Toast.LENGTH_SHORT).show()

                        }
                        is Status.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                    }
                }

            }

        }


    }
    private fun showProperty(){
        binding.rvProperity.visibility = View.VISIBLE
        val adapter =
            PropertyAdapter(supportFragmentManager,
                propertyList,
                this@CategoryActivity,
                this@CategoryActivity
            )
        binding.rvProperity.layoutManager = LinearLayoutManager(applicationContext)
        binding.rvProperity.adapter = adapter
    }

}