package com.hussein.mazaady.domain.category


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    @SerialName("ads_banners")
    var adsBanners: List<AdsBanner>,
    @SerialName("categories")
    var categories: List<CategoryX>,
    @SerialName("google_version")
    var googleVersion: String?="",
    @SerialName("huawei_version")
    var huaweiVersion: String?="",
    @SerialName("ios_latest_version")
    var iosLatestVersion: String?="",
    @SerialName("ios_version")
    var iosVersion: String?="",
    @SerialName("statistics")
    var statistics: Statistics?
)