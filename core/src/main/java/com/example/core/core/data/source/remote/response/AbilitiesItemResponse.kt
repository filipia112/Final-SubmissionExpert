package com.example.core.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class AbilitiesItemResponse(
    @field:SerializedName("displayIcon")
    val displayIcon: String? = null,

    @field:SerializedName("displayName")
    val displayName: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("slot")
    val slot: String? = null
)
