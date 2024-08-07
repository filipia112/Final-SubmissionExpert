package com.example.core.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GetAgentResponse(
	@field:SerializedName("error")
	val error: String? = null,

	@field:SerializedName("data")
	val data: List<DataAgentResponse>,

	@field:SerializedName("status")
	val status: Int? = null
)
