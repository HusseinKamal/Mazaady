package com.hussein.mazaady.domain.option


import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Option(
    @SerialName("description")
    @Contextual
    var description: Any,
    @SerialName("id")
    var id: Int,
    @SerialName("list")
    var list: Boolean,
    @SerialName("name")
    var name: String?,
    @SerialName("options")
    var options: List<OptionX>,
    @SerialName("other_value")
    @Contextual
    var otherValue: Any,
    @SerialName("parent")
    var parent: Int,
    @SerialName("slug")
    var slug: String?,
    @SerialName("type")
    @Contextual
    var type: Any,
    @SerialName("value")
    var value: String?
)