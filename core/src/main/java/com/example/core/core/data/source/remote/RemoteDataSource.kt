package com.example.core.core.data.source.remote

import android.util.Log
import com.example.core.core.data.source.remote.network.Apiresponse
import com.example.core.core.data.source.remote.network.Apiservice
import com.example.core.core.data.source.remote.response.GetAgentResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: Apiservice) {

    suspend fun getAllAgents(): Flow<Apiresponse<GetAgentResponse>> = callbackFlow {
        val client = apiService.getAllData()
        client.enqueue(object : Callback<GetAgentResponse> {
            override fun onResponse(
                call: Call<GetAgentResponse>,
                response: Response<GetAgentResponse>
            ) {
                Log.d("RemoteDataSource", "Response: ${response.body()}")
                trySend(
                    if (response.isSuccessful && response.body() != null) {
                        Apiresponse.Success(response.body()!!)
                    } else {
                        Apiresponse.Empty
                    }
                ).isSuccess
            }
            override fun onFailure(call: Call<GetAgentResponse>, t: Throwable) {
                trySend(Apiresponse.Error(t.message.toString())).isSuccess
                Log.e("RemoteDataSource", t.message.toString())
            }
        })
        awaitClose { client.cancel() }
    }

}
