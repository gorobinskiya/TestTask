package com.example.testtask.presentation.main

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtask.data.remote.NumberDto
import com.example.testtask.domain.repository.NumbersRepo
import com.example.testtask.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: NumbersRepo,
) : ViewModel() {

    private val liveData: MutableLiveData<Resource<NumberDto>> = MutableLiveData()


    fun getFactByNumber(int: Editable): MutableLiveData<Resource<NumberDto>> {
        viewModelScope.launch {
            repository.call(liveData) {
                repository.getByNumber(int.toString())
            }
        }
        return liveData
    }

    fun getRandomFact(): MutableLiveData<Resource<NumberDto>> {
        viewModelScope.launch {
            repository.call(liveData) {
                repository.getRandom()
            }
        }
        return liveData
    }

    fun getAllNumbersDataLiveData(): LiveData<List<NumberDto>> =
        repository.getAllNumbersDataLiveData()


    fun saveNumberData(item: NumberDto): Job = viewModelScope.launch {
        repository.upsert(item)
    }


}