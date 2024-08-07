package com.example.core.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class RoleAgentResponse(
    @field:SerializedName("displayIcon")
    val displayIcon: String? = null,

    @field:SerializedName("displayName")
    val displayName: String? = null,

    @field:SerializedName("assetPath")
    val assetPath: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("uuid")
    val uuid: String? = null
)
