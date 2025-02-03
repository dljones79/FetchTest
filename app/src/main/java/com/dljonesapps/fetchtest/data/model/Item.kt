package com.dljonesapps.fetchtest.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Item(
    val id: Int,
    @Json(name = "listId")
    val listId: Int,
    val name: String?
)
