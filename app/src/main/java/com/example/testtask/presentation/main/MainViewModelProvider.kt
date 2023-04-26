package com.example.testtask.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testtask.domain.repository.NumbersRepo

@Suppress("UNCHECKED_CAST")
class MainViewModelProvider(
    private val repository: NumbersRepo,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }

}