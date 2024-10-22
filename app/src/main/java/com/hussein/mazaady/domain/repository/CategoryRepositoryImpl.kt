package com.hussein.mazaady.domain.repository

import com.hussein.mazaady.data.model.BaseResponse
import com.hussein.mazaady.data.remote.ApiService
import com.hussein.mazaady.domain.category.Category
import com.hussein.mazaady.domain.option.Option
import com.hussein.mazaady.domain.properity.Properity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.hussein.mazaady.data.model.Status

class CategoryRepositoryImpl(private val apiService: ApiService) : CategoryRepository {

    override fun getCategories(): Flow<BaseResponse<Category>> = flow {
        emit(BaseResponse(code = 200, msg = "Loading...", status = Status.Loading))
        try {
            var categories:BaseResponse<Category> = apiService.getCategories()
            categories.let {category ->
                category.data?.let {
                    val mCategoryData = categories.copy(status = Status.Success(it) )
                    emit(mCategoryData)
                }
            }


        } catch (e: Exception) {
            emit(BaseResponse(code = 400, msg = "Error", status = Status.Error("e.message ?: Unknown error",400)))
        }
    }

    override fun getProperities(): Flow<BaseResponse<Properity>> = flow {
        emit(BaseResponse(code = 200, msg = "Loading...", status = Status.Loading))
        try {
            val properities:BaseResponse<Properity> = apiService.getProperities()
            emit(properities)
        } catch (e: Exception) {
            emit(BaseResponse(code = 400, msg = "Error", status = Status.Error("e.message ?: Unknown error",400)))
        }
    }

    override fun getOptions(): Flow<BaseResponse<Option>> = flow {
        emit(BaseResponse(code = 200, msg = "Loading...", status = Status.Loading))
        try {
            val options:BaseResponse<Option> = apiService.getOptions()
            emit(options)
        } catch (e: Exception) {
            emit(BaseResponse(code = 400, msg = "Error", status = Status.Error("e.message ?: Unknown error",400)))
        }
    }
}
