package com.example.testtask.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.testtask.R
import com.example.testtask.data.local.NumbersDB
import com.example.testtask.domain.repository.NumbersRepo
import com.example.testtask.domain.usecase.CheckInternetStateUseCase
import com.example.testtask.presentation.main.MainViewModel
import com.example.testtask.presentation.main.MainViewModelProvider

class MainActivity : AppCompatActivity() {
    private val checkInternetStateUseCase: CheckInternetStateUseCase by lazy {
        CheckInternetStateUseCase(application)
    }

    private var repository: NumbersRepo? = null

    val mainViewModel: MainViewModel by viewModels {
        MainViewModelProvider(repository!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = NumbersRepo(checkInternetStateUseCase, NumbersDB(this))
        setContentView(R.layout.activity_main)
    }
}

const val DETAILS: String = "DETAILS"
const val NUMBER: String = "NUMBER"