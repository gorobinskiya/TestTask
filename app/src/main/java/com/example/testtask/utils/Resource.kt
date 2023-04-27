package com.example.testtask.utils

sealed class Resource<out DATA> {

    class Success<DATA>(val data: DATA) : Resource<DATA>()

    class Error(val message: String) : Resource<Nothing>()

    object Loading : Resource<Nothing>()

}
