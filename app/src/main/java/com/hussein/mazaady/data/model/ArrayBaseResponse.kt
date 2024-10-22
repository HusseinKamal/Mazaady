package com.hussein.mazaady.data.model

import kotlinx.serialization.Serializable
@Serializable
data class ArrayBaseResponse<T: Any>(
    val code: Int,
    val msg: String,
    val data: ArrayList<T> = ArrayList(),
    var status:Status<T> = Status.Loading
)