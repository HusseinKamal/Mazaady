package com.hussein.mazaady.domain.repository

import com.hussein.mazaady.data.model.ArrayBaseResponse
import com.hussein.mazaady.data.model.BaseResponse
import com.hussein.mazaady.domain.category.Category
import com.hussein.mazaady.domain.option.Option
import com.hussein.mazaady.domain.properity.Properity
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories(): Flow<BaseResponse<Category>>
    fun getProperities(id:String): Flow<ArrayBaseResponse<Properity>>
    fun getOptions(id:String): Flow<ArrayBaseResponse<Option>>
}