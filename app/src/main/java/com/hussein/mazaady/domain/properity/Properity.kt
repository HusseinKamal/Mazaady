package com.hussein.mazaady.domain.properity


import com.hussein.mazaady.domain.option.Option
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Properity(
    @SerialName("description")
    var description: String?="",
    @SerialName("id")
    var id: Int,
    @SerialName("list")
    var list: Boolean= false,
    @SerialName("name")
    var name: String?="",
    @SerialName("options")
    var options: List<Option> = emptyList(),
    @SerialName("other_value")
    @Contextual
    var otherValue: String?="",
    @SerialName("parent")
    @Contextual
    var parent: String?="",
    @SerialName("slug")
    var slug: String?="",
    @SerialName("type")
    @Contextual
    var type: String?="",
    @SerialName("value")
    var value: String?="",
    var isVisible:Boolean = false
)