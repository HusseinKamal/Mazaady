package com.hussein.mazaady.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _properityData = MutableStateFlow<BaseResponse<Properity>>(BaseResponse(0,"",null))
    val properityData: StateFlow<BaseResponse<Properity>> = _properityData

    private val _optionData = MutableStateFlow<BaseResponse<Option>>(BaseResponse(0,"",null))
    val optionData: StateFlow<BaseResponse<Option>> = _optionData

    fun getCategories() {
        viewModelScope.launch {
            userRepository.getCategories().collect {
                _categoryData.value = it
            }
        }
    }
    fun getProperities() {
        viewModelScope.launch {
            userRepository.getProperities().collect {
                _properityData.value = it
            }
        }
    }
    fun getOptions() {
        viewModelScope.launch {
            userRepository.getOptions().collect {
                _optionData.value = it
            }
        }
    }
}