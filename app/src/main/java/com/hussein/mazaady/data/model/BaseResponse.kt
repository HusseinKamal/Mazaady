package com.hussein.mazaady.data.model

import kotlinx.serialization.Serializable
@Serializable
data class BaseResponse<T: Any>(
    val code: Int,
    val msg: String,
    val data: T? = null,
    var status:Status<T> = Status.Loading
)