package com.hussein.mazaady.domain.category


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Statistics(
    @SerialName("auctions")
    var auctions: Int,
    @SerialName("products")
    var products: Int,
    @SerialName("users")
    var users: Int
)