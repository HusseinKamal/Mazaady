package com.hussein.mazaady.domain.repository

import com.hussein.mazaady.data.model.BaseResponse
import com.hussein.mazaady.domain.category.Category
import com.hussein.mazaady.domain.option.Option
import com.hussein.mazaady.domain.properity.Properity
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories(): Flow<BaseResponse<Category>>
    fun getProperities(): Flow< BaseResponse<Properity>>
    fun getOptions(): Flow<BaseResponse<Option>>
}