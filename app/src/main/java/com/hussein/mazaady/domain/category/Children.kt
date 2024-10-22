package com.hussein.mazaady.domain.category


import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Children(
    @SerialName("children")
    @Contextual
    var children: Any?,
    @SerialName("circle_icon")
    var circleIcon: String,
    @SerialName("description")
    @Contextual
    var description: String?,
    @SerialName("disable_shipping")
    var disableShipping: Int,
    @SerialName("id")
    var id: Int,
    @SerialName("image")
    var image: String?,
    @SerialName("name")
    var name: String?,
    @SerialName("slug")
    var slug: String?
)