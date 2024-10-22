package com.hussein.mazaady.data.model

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable

@Serializable
sealed class Status<out T>  {

    @Serializable
    data class Success<out T>(val data: T) : Status<T>()

    @Serializable
    data class SuccessData<out T>(val data: List<T>) : Status<T>()

    @Serializable
    data class Error(val msg: String, val code: Int? = null) : Status<Nothing>()

    @Serializable
    object Loading : Status<Nothing>()
}