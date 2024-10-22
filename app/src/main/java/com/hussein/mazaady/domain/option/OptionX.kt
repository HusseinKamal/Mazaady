package com.hussein.mazaady.domain.option


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OptionX(
    @SerialName("child")
    var child: Boolean,
    @SerialName("id")
    var id: Int,
    @SerialName("name")
    var name: String?,
    @SerialName("parent")
    var parent: Int,
    @SerialName("slug")
    var slug: String?
)