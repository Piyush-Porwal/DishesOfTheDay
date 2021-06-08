package com.smarnomad.dishes.data.dishes.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

/**
 * Dishes Model class
 */
@Parcelize
@JsonClass(generateAdapter = true)
data class Dishes(
    @Json(name = "id")
    val id: Int? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "image_url")
    val imageUrl: String? = null,

    @Json(name = "short_desc")
    val shortDescription: String? = null,

    @Json(name = "wiki_link")
    val wikiLink: String? = null,

    @Json(name = "share_link")
    val shareLink: String? = null,

    @Json(name = "more_images")
    val moreImages: List<String>? = null
) : Parcelable