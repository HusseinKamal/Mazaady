package com.hussein.mazaady.domain.repository

import com.hussein.mazaady.data.model.ArrayBaseResponse
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

    override fun getProperities(id:String): Flow<ArrayBaseResponse<Properity>> = flow {
        emit(ArrayBaseResponse(code = 200, msg = "Loading...", status = Status.Loading))
        try {
            var properities:ArrayBaseResponse<Properity> = apiService.getProperities(id)
            properities.let {property ->
                val mPropertyData = properities.copy(status = Status.SuccessData(property.data) )
                emit(mPropertyData)
            }
        } catch (e: Exception) {
            emit(ArrayBaseResponse(code = 400, msg = "Error", status = Status.Error("e.message ?: Unknown error",400)))
        }
    }

    override fun getOptions(id:String): Flow<ArrayBaseResponse<Option>> = flow {
        emit(ArrayBaseResponse(code = 200, msg = "Loading...", status = Status.Loading))
        try {
            var options:ArrayBaseResponse<Option> = apiService.getOptions(id)
            options.let {options ->
                val newProperity = options.data
                newProperity.add(0,Option(id = 0,name = "Other",parent = 0))
                val mOptionData = options.copy(status = Status.SuccessData(newProperity) )
                emit(mOptionData)
            }
        } catch (e: Exception) {
            emit(ArrayBaseResponse(code = 400, msg = "Error", status = Status.Error("e.message ?: Unknown error",400)))
        }
    }
}
