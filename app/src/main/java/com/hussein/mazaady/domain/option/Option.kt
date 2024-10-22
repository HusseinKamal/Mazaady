package com.hussein.mazaady.domain.option


import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Option(
    @SerialName("description")
    @Contextual
    var description: String?="",
    @SerialName("id")
    var id: Int,
    @SerialName("list")
    var list: Boolean=false,
    @SerialName("name")
    var name: String?,
    @SerialName("options")
    var options: List<OptionX> = emptyList(),
    @SerialName("other_value")
    @Contextual
    var otherValue: String?= "",
    @SerialName("parent")
    var parent: Int,
    @SerialName("slug")
    var slug: String?= "",
    @SerialName("type")
    @Contextual
    var type: String?= "",
    @SerialName("value")
    var value: String?= "",
    var isChecked:Boolean = false
)