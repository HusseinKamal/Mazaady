package com.hussein.mazaady.domain.category


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdsBanner(
    @SerialName("duration")
    var duration: Int,
    @SerialName("img")
    var img: String?,
    @SerialName("media_type")
    var mediaType: String?
)