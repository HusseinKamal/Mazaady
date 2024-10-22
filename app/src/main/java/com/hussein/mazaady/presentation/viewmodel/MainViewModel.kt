package com.hussein.mazaady.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hussein.mazaady.data.model.ArrayBaseResponse
import com.hussein.mazaady.data.model.BaseResponse
import com.hussein.mazaady.domain.category.Category
import com.hussein.mazaady.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.hussein.mazaady.domain.option.Option
import com.hussein.mazaady.domain.properity.Properity

class MainViewModel(private val userRepository: CategoryRepository) : ViewModel() {
    private val _categoryData = MutableStateFlow<BaseResponse<Category>>(BaseResponse(0,"",null))
    val categoryData: StateFlow<BaseResponse<Category>> = _categoryData

    private val _properityData = MutableStateFlow<ArrayBaseResponse<Properity>>(ArrayBaseResponse(0,"",ArrayList()))
    val properityData: StateFlow<ArrayBaseResponse<Properity>> = _properityData

    private val _optionData = MutableStateFlow<ArrayBaseResponse<Option>>(ArrayBaseResponse(0,"",ArrayList()))
    val optionData: StateFlow<ArrayBaseResponse<Option>> = _optionData

    fun getCategories() {
        viewModelScope.launch {
            userRepository.getCategories().collect {
                _categoryData.value = it
            }
        }
    }
    fun getProperities(id:String) {
        viewModelScope.launch {
            userRepository.getProperities(id).collect {
                _properityData.value = it
            }
        }
    }
    fun getOptions(id:String) {
        viewModelScope.launch {
            userRepository.getOptions(id).collect {
                _optionData.value = it
            }
        }
    }
}