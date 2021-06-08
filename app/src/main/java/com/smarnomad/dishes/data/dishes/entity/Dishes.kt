package com.smarnomad.dishes.data.dishes.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


/**
 * Entity to store dishes in database
 */
@Entity(
    tableName = Dishes.TABLE_NAME,
    indices = [Index("id", unique = true)]
)
data class Dishes constructor(

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "image_url")
    var imageUrl: String? = null,

    @ColumnInfo(name = "short_desc")
    var shortDescription: String? = null,

    @ColumnInfo(name = "wiki_link")
    var wikiLink: String? = null,

    @ColumnInfo(name = "share_link")
    var shareLink: String? = null,

    @ColumnInfo(name = "more_images")
    var moreImages: List<String>? = null
) {
    fun toModel(): com.smarnomad.dishes.data.dishes.model.Dishes {
        return com.smarnomad.dishes.data.dishes.model.Dishes(
            id = id,
            name = name,
            imageUrl = imageUrl,
            shortDescription = shortDescription,
            wikiLink = wikiLink,
            shareLink = shareLink,
            moreImages = moreImages
        )
    }

    companion object {
        /**
         * Table name to store dishes
         */
        const val TABLE_NAME: String = "dishes"
    }

}