package com.example.core.core.data.source
import com.example.core.core.data.source.remote.network.Apiresponse
import com.example.core.core.utils.AppExecutors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class NetworkBoundResource<ResultType, RequestType>(
    private val mExecutor: AppExecutors
) {

    private val _result = MutableStateFlow<Resource<ResultType>>(Resource.Loading(null))
    val result: StateFlow<Resource<ResultType>> get() = _result.asStateFlow()

    init {
        CoroutineScope(Dispatchers.Main).launch {
            _result.value = Resource.Loading(null)
            val dbSource = loadFromDB().first()
            if (shouldFetch(dbSource)) {
                fetchFromNetwork()
            } else {
                _result.value = Resource.Success(dbSource)
            }
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<Apiresponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    private suspend fun fetchFromNetwork() {
        val dbSourceFlow = loadFromDB()
        _result.value = Resource.Loading(null)

        try {
            when (val apiResponse = createCall().first()) {
                is Apiresponse.Success -> {
                    withContext(Dispatchers.IO) {
                        saveCallResult(apiResponse.data)
                    }
                    _result.value = Resource.Success(loadFromDB().first())
                }
                is Apiresponse.Empty -> {
                    _result.value = Resource.Success(loadFromDB().first())
                }
                is Apiresponse.Error -> {
                    onFetchFailed()
                    _result.value = Resource.Error(apiResponse.errorMessage, dbSourceFlow.first())
                }
            }
        } catch (e: Exception) {
            onFetchFailed()
            _result.value = Resource.Error(e.message.toString(), dbSourceFlow.first())
        }
    }
}