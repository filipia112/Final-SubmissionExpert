package com.example.core.core.data.source.remote.network
import com.example.core.core.data.source.remote.response.GetAgentResponse
import retrofit2.Call
import retrofit2.http.GET

interface Apiservice {
    @GET("agents")
    fun getAllData(): Call<GetAgentResponse>
}