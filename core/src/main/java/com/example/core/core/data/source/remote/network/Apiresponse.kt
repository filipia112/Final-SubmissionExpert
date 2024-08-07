package com.example.core.core.data.source.remote.network

sealed class Apiresponse<out R> {
    data class Success<out T>(val data: T) : Apiresponse<T>()
    data class Error(val errorMessage: String) : Apiresponse<Nothing>()
    data object Empty : Apiresponse<Nothing>()
}