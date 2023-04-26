package com.example.testtask.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testtask.data.local.NumbersDB
import com.example.testtask.data.remote.NumberDto
import com.example.testtask.data.remote.RetrofitInstance

import com.example.testtask.domain.usecase.CheckInternetStateUseCase
import com.example.testtask.utils.Resource
import retrofit2.Response
import java.io.IOException

class NumbersRepo(
    private val checkInternetStateUseCase: CheckInternetStateUseCase,
    private val numbersDB: NumbersDB
) {
    fun getAllNumbersDataLiveData(): LiveData<List<NumberDto>> =
        numbersDB.getNumbersDao().getAllNumbersDataLiveData()


    suspend fun upsert(item: NumberDto) {
        numbersDB.getNumbersDao().upsert(item)
    }

    suspend fun getByNumber(string: String): Response<NumberDto> {
        return RetrofitInstance.api.getByNumber(string)
    }

    suspend fun getRandom(): Response<NumberDto> {
        return RetrofitInstance.api.getRandom()
    }

    suspend fun <T> call(
        liveData: MutableLiveData<Resource<T>>,
        getResponse: suspend () -> Response<T>,
    ) {
        liveData.postValue(Resource.Loading)
        try {
            if (checkInternetStateUseCase.isInternetAvailable()) {
                liveData.postValue(handleResponse(getResponse()))
            } else {
                liveData.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> liveData.postValue(Resource.Error("Network failure"))
                else -> liveData.postValue(Resource.Error("Conversion Error: $t"))
            }
        }
    }

    private fun <T> handleResponse(response: Response<T>): Resource<T> {
        if (response.isSuccessful) {
            response.body()?.let { currencyResponse ->
                return Resource.Success(currencyResponse)
            }
        }

        return Resource.Error(response.message())
    }


}
